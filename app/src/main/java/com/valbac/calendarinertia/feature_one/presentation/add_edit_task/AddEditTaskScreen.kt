package com.valbac.calendarinertia.feature_one.presentation.add_edit_task

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddEditTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                    navigator.popBackStack()
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save note",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = state.value.title,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.SetTitle(it)) },
                label = { Text("Titel") },
                placeholder = { Text("Titel...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            TextField(
                value = state.value.description,
                onValueChange = { viewModel.onEvent(AddEditTaskEvent.SetDescription(it)) },
                label = { Text("Description") },
                placeholder = { Text("Description...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}