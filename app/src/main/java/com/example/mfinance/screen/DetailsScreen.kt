package com.example.mfinance.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mfinance.components.AnimatedCircleWithText
import com.example.mfinance.components.ListOfTransactions
import com.example.mfinance.components.SpentTypeComponent
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(modifier: Modifier = Modifier) {
    Scaffold { paddings ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            val calendar = Calendar.getInstance()
            var startDate by remember {
                mutableLongStateOf(calendar.timeInMillis)
            }
            var endDate by remember {
                mutableLongStateOf(calendar.timeInMillis)
            }
            val dateRangePickerState = rememberDateRangePickerState(
                initialSelectedStartDateMillis = startDate,
                initialSelectedEndDateMillis = endDate
            )

            var showRangePickerDialog by remember {
                mutableStateOf(false)
            }
            Text(
                "Calculated range ${getFormattedDate(startDate)} - ${getFormattedDate(endDate)}",
                modifier.clickable { showRangePickerDialog = true })
            if (showRangePickerDialog){
                DatePickerDialog(
                    onDismissRequest = { showRangePickerDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showRangePickerDialog = false
                            startDate = dateRangePickerState.selectedStartDateMillis!!
                            endDate = dateRangePickerState.selectedEndDateMillis!!
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showRangePickerDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }) {
                    DateRangePicker(
                        state = dateRangePickerState,
                        modifier = Modifier.height(height = 500.dp) // if I don't set this, dialog's buttons are not appearing
                    )
                }
            }
            AnimatedCircleWithText()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                SpentTypeComponent(color = Color.Green)
                SpentTypeComponent(color = Color.Red)
                SpentTypeComponent(color = Color.Yellow)
                SpentTypeComponent(color = Color.Blue)
            }
            Spacer(Modifier.height(10.dp))
            ListOfTransactions()
        }
    }
}

fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}

