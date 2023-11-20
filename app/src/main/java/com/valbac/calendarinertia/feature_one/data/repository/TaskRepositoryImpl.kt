package com.valbac.calendarinertia.feature_one.data.repository

import com.valbac.calendarinertia.feature_one.data.local.TaskDao
import com.valbac.calendarinertia.feature_one.domain.model.TaskEntity
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(taskEntity: TaskEntity) {
        return dao.upsertTask(taskEntity)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        return dao.deleteTask(taskEntity)
    }

    override fun getTasks(): Flow<List<TaskEntity>> {
        return dao.getTasks()
    }

}