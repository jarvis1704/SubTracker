package com.biprangshu.subtracker.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.biprangshu.subtracker.navigation.Route
import com.biprangshu.subtracker.showCurrencySetModal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BudgetSetModal(
    modifier: Modifier = Modifier,
    onOnboardComplete: (budget: Double, currency: String, Route) -> Unit
) {

    var budgetAmount by remember {
        mutableStateOf("")
    }

    var currency by remember {
        mutableStateOf("$")
    }

    if(showCurrencySetModal){
        ModalBottomSheet(
            onDismissRequest = {
                showCurrencySetModal = false
            },
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                Text(
                    text = "Set Your Budget",
                    style = MaterialTheme.typography.headlineSmallEmphasized
                )
                Spacer(modifier= modifier.height(8.dp))
                Text(
                    text = "Choose a monthly budget to help you manage your subscriptions effectively.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.alpha(0.7f)
                )
                Spacer(Modifier.height(16.dp))
                //textfield for budget input can be added here
                TextField(
                    value = budgetAmount,
                    onValueChange = { newValue ->
                        // Validate input to ensure only numbers
                        if (newValue.all { it.isDigit() }) {
                            budgetAmount = newValue
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        textAlign = TextAlign.Center,
                        color = colorScheme.primary
                    ),
                    placeholder = {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.displayLarge.copy(
                                textAlign = TextAlign.Center,
                                color = colorScheme.onSurface.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = colorScheme.primary
                    )
                )
                Spacer(Modifier.height(16.dp))
                //currency selector
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Currency",
                        style = MaterialTheme.typography.titleLargeEmphasized
                    )
                    //textfield for currency choice
                    TextField(
                        value = currency,
                        onValueChange = { newValue ->
                            //todo: add validation for currency input
                            currency = newValue
                        },
                        textStyle = MaterialTheme.typography.titleLargeEmphasized.copy(
                            textAlign = TextAlign.End,
                            color = colorScheme.primary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = colorScheme.primary
                        )
                    )
                }

                //confirm buttton
                Spacer(Modifier.height(16.dp))
                FilledTonalButton(
                    onClick = {
                        //handle budget confirmation logic here
                        onOnboardComplete(
                            if(budgetAmount.isNotEmpty()) budgetAmount.toDouble() else 0.0,
                            currency,
                            Route.HomeScreen
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = if(budgetAmount.isNotEmpty()){
                        ButtonDefaults.filledTonalButtonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary
                        )
                    } else {
                        ButtonDefaults.filledTonalButtonColors()
                    }
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Confirm",
                            style = MaterialTheme.typography.titleMediumEmphasized
                        )
                    }
                }
            }
        }
    }
}