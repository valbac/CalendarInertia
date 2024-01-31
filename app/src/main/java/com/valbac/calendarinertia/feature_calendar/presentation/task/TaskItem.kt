package com.valbac.calendarinertia.feature_calendar.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .alpha(
                if (task.isDone) 0.25f else 1.0f
            )
            .padding(start = 12.dp, end = 12.dp, bottom = 6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(task.color))

    ) {
        Row(
            modifier = modifier.height(50.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = Color.DarkGray,
                            offset = Offset(x = 2f, y = 4f),
                            blurRadius = 0.5f
                        )
                    ),
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Checkbox(
                modifier = Modifier.padding(vertical = 12.dp),
                checked = task.isDone,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Black,
                    checkedColor = Color.White,
                    checkmarkColor = Color.Black
                ),
                onCheckedChange = { isChecked ->
                    viewModel.onEvent(TaskEvent.OnDoneChange(task, isChecked))
                }
            )
            Column(
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                Text(
                    text = "${task.dateDay}.${task.dateMonth}.${task.dateYear}",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleMedium.copy(
                        shadow = Shadow(
                            color = Color.DarkGray,
                            offset = Offset(x = 2f, y = 4f),
                            blurRadius = 0.5f
                        )
                    )
                )
                Text(
                    text = "${task.hour}:${task.minute}:${task.second}",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleMedium.copy(
                        shadow = Shadow(
                            color = Color.DarkGray,
                            offset = Offset(x = 2f, y = 4f),
                            blurRadius = 0.5f
                        )
                    )
                )
            }
            IconButton(
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = onDeleteClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note",
                    tint = Color.Black
                )
            }

        }
        if (task.description.isNotBlank()) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = "${task.description}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(
                            color = Color.DarkGray,
                            offset = Offset(x = 2f, y = 4f),
                            blurRadius = 0.5f
                        )
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}