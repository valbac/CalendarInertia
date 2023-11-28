package com.valbac.calendarinertia.feature_one.presentation.task

import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity

sealed interface TaskEvent {
    data class DeleteTask(val task: TaskEntity): TaskEvent
    object RestoreNote: TaskEvent
    data class OnDoneChange(val task: TaskEntity, val isDone: Boolean): TaskEvent
}