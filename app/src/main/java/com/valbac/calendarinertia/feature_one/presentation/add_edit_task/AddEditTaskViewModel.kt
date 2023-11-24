package com.valbac.calendarinertia.feature_one.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = mutableStateOf(AddEditTaskState())
    val state: State<AddEditTaskState> = _state

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.SaveTask -> {

                val title = state.value.title
                val description = state.value.description
                val color = state.value.color
                val dateDay = state.value.dateDay
                val dateMonth = state.value.dateMonth
                val dateYear = state.value.dateYear
                val hour = state.value.hour
                val minute = state.value.minute
                val second = state.value.second

                if (title.isBlank() || description.isBlank()) {
                    return
                }

                val task = TaskEntity(
                    title = title,
                    description = description,
                    isDone = false,
                    color = color,
                    dateDay = dateDay,
                    dateMonth = dateMonth,
                    dateYear = dateYear,
                    hour = hour,
                    minute = minute,
                    second = second,
                )

                viewModelScope.launch {
                    repository.upsertTask(task)
                }
                _state.value = state.value.copy(
                    title = "",
                    description = ""
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
        }
    }
}
