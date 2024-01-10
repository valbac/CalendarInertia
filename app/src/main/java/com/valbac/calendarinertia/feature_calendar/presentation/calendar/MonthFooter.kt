package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import java.time.YearMonth

@Composable
fun MonthFooter(currentMonth: YearMonth) {
    Box(
        Modifier
            .fillMaxWidth()
            .testTag("MonthFooter")
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${currentMonth.month} - ${currentMonth.year}",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}