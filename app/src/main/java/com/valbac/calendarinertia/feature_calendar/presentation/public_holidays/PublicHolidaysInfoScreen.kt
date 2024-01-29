package com.valbac.calendarinertia.feature_calendar.presentation.public_holidays

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valbac.calendarinertia.feature_calendar.presentation.calendar.CalendarViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PublicHolidaysInfoScreen(
    viewModelCalendar: CalendarViewModel = hiltViewModel()
) {
    val publicHolidaysDE by viewModelCalendar.getPublicHolidaysDEFlow().collectAsState(initial = emptyList())
    val publicHolidaysDESorted = publicHolidaysDE.sortedBy { it.date }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        for (publicHoliday in publicHolidaysDESorted) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.errorContainer)
            ) {
                Row(
                    modifier = Modifier.height(40.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 5.dp)
                            .padding(start = 6.dp)
                    ) {
                        Text(
                            text = "${publicHoliday.date.split("-").reversed().toTypedArray().joinToString ("-")} - ${publicHoliday.localName}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    offset = Offset(x = 2f, y = 4f),
                                    blurRadius = 0.5f
                                )
                            ),
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
                if (publicHoliday.counties != null || publicHoliday.launchYear != null) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .padding(bottom = 6.dp)
                    ) {
                        val textValue = listOfNotNull(
                            publicHoliday.counties?.let { "Only for Landkreis: $it" },
                            publicHoliday.launchYear?.let { "Launch year: $it" }
                        ).joinToString("\n")
                        Text(
                            text = textValue,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    offset = Offset(x = 2f, y = 4f),
                                    blurRadius = 0.5f
                                )
                            ),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}