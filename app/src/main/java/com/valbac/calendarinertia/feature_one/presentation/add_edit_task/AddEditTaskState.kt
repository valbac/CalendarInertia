package com.valbac.calendarinertia.feature_one.presentation.add_edit_task

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val isAddingTask: Boolean = false
)
