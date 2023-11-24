package com.valbac.calendarinertia.feature_one.presentation.add_edit_task


sealed interface AddEditTaskEvent {
    object SaveTask: AddEditTaskEvent
    data class SetTitle(val title: String): AddEditTaskEvent
    data class SetDescription(val description: String): AddEditTaskEvent
    data class SetColor(val color: Int): AddEditTaskEvent
    data class SetTime(val hour: Int, val minute: Int, val second: Int): AddEditTaskEvent
    data class SetDate(val dateDay: Int, val dateMonth: Int, val dateYear: Int,): AddEditTaskEvent
}