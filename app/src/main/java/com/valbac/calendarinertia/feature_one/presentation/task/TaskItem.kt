package com.valbac.calendarinertia.feature_one.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    viewModel: TaskViewModel = hiltViewModel(),
    onDeleteClick: () -> Unit
) {
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
        Column (
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = {isChecked ->
                    viewModel.onEvent(TaskEvent.OnDoneChange(task, isChecked))
                }
            )
        }
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
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
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }
    }
}