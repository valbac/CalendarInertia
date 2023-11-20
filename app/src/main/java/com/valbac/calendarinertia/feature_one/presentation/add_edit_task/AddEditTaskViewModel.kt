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
            AddEditTaskEvent.SaveTask -> {
                val title = state.value.title
                val description = state.value.description

                if (title.isBlank() || description.isBlank()) {
                    return
                }

                val task = TaskEntity(
                    title = title,
                    description = description,
                    isDone = false
                )

                viewModelScope.launch {
                    repository.upsertTask(task)
                }
                _state.value = state.value.copy(
                    isAddingTask = false,
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
        }
    }
}
