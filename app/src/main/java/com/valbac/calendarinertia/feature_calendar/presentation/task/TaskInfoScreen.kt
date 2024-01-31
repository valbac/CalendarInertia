package com.valbac.calendarinertia.feature_calendar.presentation.task

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_calendar.presentation.destinations.AddEditTaskScreenDestination
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TaskInfoScreen(
    navigator: DestinationsNavigator,
    viewModel: TaskViewModel = hiltViewModel()
) {

    val tasks = viewModel.tasks.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddEditTaskScreenDestination(0)){
                    }
                }
            )
            {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column {
            LazyColumn(

            ) {
                items(tasks.value) { task ->
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