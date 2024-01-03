package com.valbac.calendarinertia.feature_calendar.domain.repository

import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(taskEntity: TaskEntity)

    suspend fun deleteTask(taskEntity: TaskEntity)

    fun getTasks(): Flow<List<TaskEntity>>

    suspend fun getTaskById(id: Int): TaskEntity?
}