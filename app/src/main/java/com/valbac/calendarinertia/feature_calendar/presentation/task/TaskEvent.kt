package com.valbac.calendarinertia.feature_calendar.presentation.task

import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity

sealed interface TaskEvent {

    data class DeleteTask(val task: TaskEntity): TaskEvent
    object RestoreNote: TaskEvent
    data class OnDoneChange(val task: TaskEntity, val isDone: Boolean): TaskEvent
}