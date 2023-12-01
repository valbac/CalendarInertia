package com.valbac.calendarinertia.feature_one.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks = repository.getTasks()
    private var recentlyDeletedTask: TaskEntity? = null

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    repository.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }
            }

            is TaskEvent.RestoreNote -> {
                viewModelScope.launch {
                    repository.upsertTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }

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

