package com.valbac.calendarinertia.feature_calendar.presentation.add_edit_task

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val color: Int = 0,
    val dateDay: Int = 0,
    val dateMonth: Int = 0,
    val dateYear: Int = 0,
    val hour: Int = -1,
    val minute: Int = -1,
    val second: Int = -1,
    val isDone: Boolean = false,
    val checked: Boolean = false,
    val id: Int = 0,
)
