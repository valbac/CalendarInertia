package com.valbac.calendarinertia.feature_calendar.presentation.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextOverflow
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
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun DayInfoScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    viewModelCalendar: CalendarViewModel = hiltViewModel(),
    day: CalendarDay,
    navigator: DestinationsNavigator
) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())
    val publicHolidaysDE =
        viewModelCalendar.publicHolidaysDEFlow.collectAsState(initial = emptyList())
    val sortedTaskList =
        tasks.value.sortedWith(compareBy({ it.hour }, { it.minute }, { it.second }))
    val publicHolidaysDESorted =
        publicHolidaysDE.value.sortedBy { it.date }
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
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
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
            modifier = Modifier.padding(innerPadding),
        ) {
            for (publicHoliday in publicHolidaysDESorted) {
                val composedDate = LocalDate.of(day.date.year, day.date.month, day.date.dayOfMonth)
                if (composedDate == LocalDate.parse(publicHoliday.date)) {
                    Column(
                        modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer)
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
                                    text = publicHoliday.localName,
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
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }

            LazyColumn(
                
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