package com.valbac.calendarinertia.feature_one.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.ramcosta.composedestinations.annotation.Destination
import com.valbac.calendarinertia.feature_one.presentation.task.TaskEvent
import com.valbac.calendarinertia.feature_one.presentation.task.TaskViewModel

@Destination
@Composable
fun DayInfoScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    day: CalendarDay,
) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())

    Column {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = day.date.toString(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks.value) { task ->
                if (day.date.dayOfMonth == task.dateDay && day.date.month.value == task.dateMonth && day.date.year == task.dateYear) {

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .background(color = Color(task.color))
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = task.title,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${task.description}",
                                fontSize = 14.sp
                            )
                        }
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                        ) {
                            Text(
                                text = "${task.dateDay}.${task.dateMonth}.${task.dateYear}",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "${task.hour}:${task.minute}:${task.second}",
                                fontSize = 14.sp
                            )
                        }
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                        ) {
                            IconButton(onClick = {
                                viewModel.onEvent(TaskEvent.DeleteTask(task))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Task"
                                )
                            }
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = { isChecked ->
                                    viewModel.onEvent(TaskEvent.OnDoneChange(task, isChecked))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}