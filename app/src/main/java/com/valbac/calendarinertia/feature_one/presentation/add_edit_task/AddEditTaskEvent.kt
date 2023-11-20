package com.valbac.calendarinertia.feature_one.presentation.add_edit_task


sealed interface AddEditTaskEvent {
    object SaveTask: AddEditTaskEvent
    data class SetTitle(val title: String): AddEditTaskEvent
    data class SetDescription(val description: String): AddEditTaskEvent
}