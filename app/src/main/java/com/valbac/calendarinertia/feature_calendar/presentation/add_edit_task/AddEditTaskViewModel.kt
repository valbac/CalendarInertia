package com.valbac.calendarinertia.feature_calendar.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity
import com.valbac.calendarinertia.feature_calendar.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditTaskState())
    val state: State<AddEditTaskState> = _state

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    init {
        savedStateHandle.get<Int>("id")?.let {
            val id = it
            viewModelScope.launch {
                repository.getTaskById(id)?.let {task ->
                    _state.value = state.value.copy(
                        title = task.title,
                        description = task.description,
                        isDone = task.isDone,
                        color = task.color,
                        dateDay = task.dateDay,
                        dateMonth = task.dateMonth,
                        dateYear = task.dateYear,
                        hour = task.hour,
                        minute = task.minute,
                        second = task.second,
                        id = task.id,
                        checked = task.checked
                    )
                }
            }
        }
    }

    fun validateInputs(title: String, color: Int, dateDay: Int, hour: Int): Boolean {
        var isValid = true
        if (title.isBlank() || color >= 0 || dateDay <= 0 || hour < 0) {
            _showDialog.value = true
            isValid = false
        } else {
            _showDialog.value = false
        }
        return isValid
    }


    fun closeDialog() {
        _showDialog.value = false
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.SaveTask -> {

                val title = state.value.title
                val description = state.value.description
                val isDone = state.value.isDone
                val color = state.value.color
                val dateDay = state.value.dateDay
                val dateMonth = state.value.dateMonth
                val dateYear = state.value.dateYear
                val hour = state.value.hour
                val minute = state.value.minute
                val second = state.value.second
                val id = state.value.id
                val checked = state.value.checked

                val task = TaskEntity(
                    title = title,
                    description = description,
                    isDone = isDone,
                    color = color,
                    dateDay = dateDay,
                    dateMonth = dateMonth,
                    dateYear = dateYear,
                    hour = hour,
                    minute = minute,
                    second = second,
                    id = id,
                    checked = checked
                )

                viewModelScope.launch {
                    repository.upsertTask(task)
                }
                _state.value = state.value.copy(
                    title = "",
                    description = "",
                    isDone = false,
                    color = 0,
                    dateDay = 0,
                    dateMonth = 0,
                    dateYear = 0,
                    hour = -1,
                    minute = -1,
                    second = -1,
                    id = 0,
                    checked = false
                )
            }

            is AddEditTaskEvent.SetDescription -> {
                _state.value = state.value.copy(
                    description = event.description
                )
            }

            is AddEditTaskEvent.SetTitle -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }

            is AddEditTaskEvent.SetTime -> {
                _state.value = state.value.copy(
                    hour = event.hour,
                    minute = event.minute,
                    second = event.second
                )
            }

            is AddEditTaskEvent.SetDate -> {
                _state.value = state.value.copy(
                    dateDay = event.dateDay,
                    dateMonth = event.dateMonth,
                    dateYear = event.dateYear
                )
            }

            is AddEditTaskEvent.SetColor -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            is AddEditTaskEvent.SetReminder -> {
                _state.value = state.value.copy(
                    checked = event.checked
                )
            }
        }
    }
}
