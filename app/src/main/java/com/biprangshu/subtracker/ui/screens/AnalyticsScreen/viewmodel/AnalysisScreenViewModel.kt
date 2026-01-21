package com.biprangshu.subtracker.ui.screens.AnalyticsScreen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.biprangshu.subtracker.data.local.ForecastDao
import com.biprangshu.subtracker.data.local.ForecastEntity
import com.biprangshu.subtracker.data.local.InsightDao
import com.biprangshu.subtracker.data.local.InsightEntity
import com.biprangshu.subtracker.data.local.PriceAlertDao
import com.biprangshu.subtracker.data.local.PriceAlertEntity
import com.biprangshu.subtracker.data.local.UserEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import com.biprangshu.subtracker.domain.usecase.GetUserDataUserCase
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.state.ChatMessage
import com.biprangshu.subtracker.ui.screens.AnalyticsScreen.state.ChatUiState
import com.biprangshu.subtracker.worker.BurnRateWorker
import com.biprangshu.subtracker.worker.PriceIncreaseWorker
import com.biprangshu.subtracker.worker.SubOptimizerWorker
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalysisScreenViewModel @Inject constructor(
    private val userDataUserCase: GetUserDataUserCase,
    private val subscriptionRepository: SubscriptionRepository,
    private val insightDao: InsightDao,
    private val priceAlertDao: PriceAlertDao,
    private val forecastDao: ForecastDao,
    @ApplicationContext private val context: Context
): ViewModel() {

    //todo: implement viewmodel logic for Analytics Screen

    val userData: StateFlow<UserEntity?> = userDataUserCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val totalMonthlySpend: StateFlow<Double> = subscriptionRepository.getTotalMonthlySpend()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    private val allSubscriptions = subscriptionRepository.getAllSubscriptions()

    val monthlyChartData: StateFlow<List<Double>> = allSubscriptions.map { subs ->
        val monthlyTotals = MutableList(12) { 0.0 }
        val calendar = Calendar.getInstance()

        subs.forEach { sub ->
            if (sub.billingCycle == "Monthly") {
                // Add to all months
                for (i in 0..11) {
                    monthlyTotals[i] += sub.price
                }
            } else if (sub.billingCycle == "Yearly") {

                calendar.timeInMillis = sub.firstPaymentDate
                val month = calendar.get(Calendar.MONTH)
                if (month in 0..11) {
                    monthlyTotals[month] += sub.price
                }
            }
        }
        monthlyTotals
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = List(12) { 0.0 }
    )

    val subscriptionBreakdownData: StateFlow<List<Subscription>> = allSubscriptions.map { subs ->
        subs.sortedByDescending { it.price }.take(5)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val hasSubscriptions: StateFlow<Boolean> = allSubscriptions.map { it.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val aiInsights: StateFlow<List<InsightEntity>> = insightDao.getAllInsights()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val forecastData: StateFlow<List<ForecastEntity>> = forecastDao.getForecasts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val priceAlerts: StateFlow<List<PriceAlertEntity>> = priceAlertDao.getAllAlerts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )



    fun refreshAnalysis() {

        //insight generation
        val workRequest = OneTimeWorkRequestBuilder<SubOptimizerWorker>()
            .addTag("SubOptimizerManual")
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)

        //burn rate analysis
        val burnRateRequest = OneTimeWorkRequestBuilder<BurnRateWorker>()
            .addTag("BurnRateManual")
            .build()

        //price increase check
        val priceIncreaseRequest = OneTimeWorkRequestBuilder<PriceIncreaseWorker>()
            .addTag("WatchdogManual")
            .build()

        WorkManager.getInstance(context).enqueue(listOf(workRequest, burnRateRequest, priceIncreaseRequest))
    }

    //ai chat logic
    private val _chatState = MutableStateFlow(ChatUiState())
    val chatState = _chatState.asStateFlow()

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return


        _chatState.update {
            it.copy(
                messages = it.messages + ChatMessage(text = userMessage, isUser = true),
                isLoading = true
            )
        }

        viewModelScope.launch {
            try {
                val subscriptions = allSubscriptions.first()
                val currency = userData.value?.preferredCurrency ?: "$"

                val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
                val subListContext = subscriptions.joinToString("\n") { sub ->
                    val nextDue = if (sub.nextPaymentDate > 0) dateFormat.format(Date(sub.nextPaymentDate)) else "Unknown"
                    "- ${sub.name}: ${sub.price} ${sub.currency} (${sub.billingCycle}, Category: ${sub.category}, Next Due: $nextDue)"
                }

                val today = dateFormat.format(Date())


                val prompt = """
                    You are a helpful Finance Assistant for a subscription tracking app.
                    
                    **Context**:
                    - Current Date: $today
                    - User's Preferred Currency: "$currency"
                    - **Region Inference**: Infer the user's region based on the currency (e.g., '₹' = India, '£' = UK, '€' = Europe). If generic ('$'), look for regional pricing cues or default to US.
                    
                    **User's Subscriptions**:
                    $subListContext
                    
                    **User Question**: "$userMessage"
                    
                    **Instructions**:
                    - Answer concisely based on the data provided.
                    - **Region Awareness**: When making suggestions (alternatives, bundles, cancellations), ensure they are relevant to the inferred region. Do NOT suggest services not available in that region (e.g., Hulu in India).
                    - If asked about "savings", suggest cancelling unused/high-cost items, keeping local purchasing power in mind.
                    - Perform calculations (sums, differences) accurately.
                    - Be friendly but direct.
                """.trimIndent()

                val generativeModel = Firebase.ai(backend = GenerativeBackend.googleAI())
                    .generativeModel("gemini-3-flash-preview")

                val response = generativeModel.generateContent(prompt)
                val aiReplyText = response.text ?: "I couldn't analyze that right now."


                _chatState.update {
                    it.copy(
                        messages = it.messages + ChatMessage(text = aiReplyText, isUser = false),
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _chatState.update {
                    it.copy(
                        messages = it.messages + ChatMessage(text = "Error: ${e.localizedMessage}", isUser = false),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearChatError() {
        _chatState.update { it.copy(error = null) }
    }

    fun dismissAlert(alert: PriceAlertEntity) {
        viewModelScope.launch {
            priceAlertDao.deleteAlert(alert)
        }
    }

}




