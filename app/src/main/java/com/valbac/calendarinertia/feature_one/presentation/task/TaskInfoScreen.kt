package com.valbac.calendarinertia.feature_one.presentation.task

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valbac.calendarinertia.feature_one.presentation.add_edit_task.AddEditTaskViewModel
import com.valbac.calendarinertia.feature_one.presentation.destinations.AddEditTaskScreenDestination
import com.valbac.calendarinertia.feature_one.presentation.destinations.SettingsScreenDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TaskInfoScreen(
    navigator: DestinationsNavigator,
    viewModel: TaskViewModel
) {
    val state = viewModel.state

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
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.value.tasks) {task ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column (
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${task.title}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${task.description}",
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(TaskEvent.DeleteTask(task))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Task")
                    }
                }
            }
        }
    }
}