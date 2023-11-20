package com.valbac.calendarinertia.feature_one.presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = mutableStateOf (TaskState())
    val state: State<TaskState> = _state

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                }
            }
            TaskEvent.RestoreNote -> TODO( )
        }
    }
}

