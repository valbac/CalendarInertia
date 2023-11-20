package com.valbac.calendarinertia.feature_one.domain.repository

import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(taskEntity: TaskEntity)

    suspend fun deleteTask(taskEntity: TaskEntity)

    fun getTasks(): Flow<List<TaskEntity>>
}