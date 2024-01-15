package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_calendar.presentation.destinations.AddEditTaskScreenDestination
import com.valbac.calendarinertia.feature_calendar.presentation.task.TaskEvent
import com.valbac.calendarinertia.feature_calendar.presentation.task.TaskItem
import com.valbac.calendarinertia.feature_calendar.presentation.task.TaskViewModel
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
    val sortedTaskList =
        tasks.value.sortedWith(compareBy({ it.hour }, { it.minute }, { it.second }))
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navigator.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Menu"
                    )
                }
            },
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "${day.date.dayOfMonth} - ${day.date.month.toString().lowercase()}",
                        fontSize = 20.sp
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddEditTaskScreenDestination(0))
                }
            )
            {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sortedTaskList) { task ->
                    if (day.date.dayOfMonth == task.dateDay && day.date.month.value == task.dateMonth && day.date.year == task.dateYear) {
                        TaskItem(
                            task = task,
                            modifier = Modifier.clickable {
                                navigator.navigate(AddEditTaskScreenDestination(task.id))
                            }
                        )
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