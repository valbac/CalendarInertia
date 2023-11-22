package com.valbac.calendarinertia.feature_one.presentation.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    viewModel: TaskViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
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
                contentDescription = "Delete Task"
            )
        }
    }
}