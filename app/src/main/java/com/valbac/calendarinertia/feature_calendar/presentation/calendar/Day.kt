package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_calendar.presentation.destinations.DayInfoScreenDestination
import com.valbac.calendarinertia.feature_calendar.presentation.task.TaskViewModel
import com.valbac.calendarinertia.ui.theme.GreenGrey60
import com.valbac.calendarinertia.ui.theme.Red40
import java.time.LocalDate

@Composable
fun Day(
    viewModelTask: TaskViewModel = hiltViewModel(),
    viewModelCalendar: CalendarViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    day: CalendarDay,
    isToday: Boolean,
) {
    val tasks = viewModelTask.tasks.collectAsState(initial = emptyList())
    val publicHolidaysDE =
        viewModelCalendar.publicHolidaysDEFlow.collectAsState(initial = emptyList())
    val scrollState = rememberScrollState()

    val publicHolidaysDESorted =
        publicHolidaysDE.value.sortedBy { it.date }

    val sortedTaskList =
        tasks.value.sortedWith(compareBy({ it.hour }, { it.minute }, { it.second }))


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .border(
                width = 0.dp,
                color = Color.Transparent,
            )
            .padding(1.dp)
            .background(
                color = when {
                    isToday -> MaterialTheme.colorScheme.inversePrimary
                    day.position == DayPosition.MonthDate -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.secondaryContainer
                }
            )
            .clickable(
                onClick = { navigator.navigate(DayInfoScreenDestination(day = day)) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 3.dp),
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 12.sp,
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            for (publicHoliday in publicHolidaysDESorted) {
                val composedDate = LocalDate.of(day.date.year, day.date.month, day.date.dayOfMonth)
                if (composedDate == LocalDate.parse(publicHoliday.date)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(MaterialTheme.colorScheme.errorContainer),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    offset = Offset(x = 2f, y = 4f),
                                    blurRadius = 0.5f
                                )
                            ),
                            text = publicHoliday.localName,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            for (task in sortedTaskList) {
                if (day.date.dayOfMonth == task.dateDay && day.date.month.value == task.dateMonth && day.date.year == task.dateYear) {
                    Box(
                        modifier = Modifier
                            .alpha(
                                if (task.isDone) 0.25f else 1.0f
                            )
                            .fillMaxWidth()
                            .height(15.dp)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(task.color)),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                shadow = Shadow(
                                    color = Color.DarkGray,
                                    offset = Offset(x = 2f, y = 4f),
                                    blurRadius = 0.5f
                                )
                            ),
                            text = task.title,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
