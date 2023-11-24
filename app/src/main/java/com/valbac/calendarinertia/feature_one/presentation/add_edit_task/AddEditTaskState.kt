package com.valbac.calendarinertia.feature_one.presentation.add_edit_task

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val color: Int = 0,
    val dateDay: Int = 0,
    val dateMonth: Int = 0,
    val dateYear: Int = 0,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
)
