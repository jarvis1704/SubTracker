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
import com.biprangshu.subtracker.data.local.UserEntity
import com.biprangshu.subtracker.domain.model.Subscription
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import com.biprangshu.subtracker.domain.usecase.GetUserDataUserCase
import com.biprangshu.subtracker.worker.BurnRateWorker
import com.biprangshu.subtracker.worker.SubOptimizerWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AnalysisScreenViewModel @Inject constructor(
    private val userDataUserCase: GetUserDataUserCase,
    private val subscriptionRepository: SubscriptionRepository,
    private val insightDao: InsightDao,
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

        WorkManager.getInstance(context).enqueue(listOf(workRequest, burnRateRequest))
    }



}




