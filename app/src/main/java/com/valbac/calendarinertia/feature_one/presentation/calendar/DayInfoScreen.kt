package com.valbac.calendarinertia.feature_one.presentation.calendar

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_one.presentation.destinations.AddEditTaskScreenDestination
import com.valbac.calendarinertia.feature_one.presentation.task.TaskEvent
import com.valbac.calendarinertia.feature_one.presentation.task.TaskItem
import com.valbac.calendarinertia.feature_one.presentation.task.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun DayInfoScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    day: CalendarDay,
    navigator: DestinationsNavigator
) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())
    val sortedTaskList = tasks.value.sortedWith(compareBy({ it.hour }, { it.minute }, { it.second }))
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddEditTaskScreenDestination)
                }
            )
            {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
    {
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
                items(sortedTaskList) { task ->
                    if (day.date.dayOfMonth == task.dateDay && day.date.month.value == task.dateMonth && day.date.year == task.dateYear) {
                        TaskItem(task = task)
                        {
                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TaskEvent.RestoreNote)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}