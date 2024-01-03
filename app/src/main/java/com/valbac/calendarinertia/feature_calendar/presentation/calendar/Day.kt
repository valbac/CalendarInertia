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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_calendar.presentation.destinations.DayInfoScreenDestination
import com.valbac.calendarinertia.feature_calendar.presentation.task.TaskViewModel

@Composable
fun Day(
    viewModel: TaskViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    day: CalendarDay,
    isToday: Boolean,
) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())
    val scrollState = rememberScrollState()

    val sortedTaskList = tasks.value.sortedWith(compareBy({ it.hour }, { it.minute }, { it.second }))

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
                    isToday -> Color.Gray
                    day.position == DayPosition.MonthDate -> Color.DarkGray
                    else -> Color.LightGray
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
            color = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray,
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
            for (task in sortedTaskList) {
                if (day.date.dayOfMonth == task.dateDay && day.date.month.value == task.dateMonth && day.date.year == task.dateYear){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(task.color)),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            fontSize = 10.sp,
                            text = task.title,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}