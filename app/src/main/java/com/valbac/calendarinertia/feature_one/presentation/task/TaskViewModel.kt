package com.valbac.calendarinertia.feature_one.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    /*private val _state = mutableStateOf(TaskState())
    val state: State<TaskState> = _state*/


    val tasks = repository.getTasks()

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                }
            }

            is TaskEvent.RestoreNote -> TODO()

            is TaskEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.upsertTask(
                        event.task.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }
}

