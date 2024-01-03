package com.valbac.calendarinertia.feature_calendar.data.repository

import com.valbac.calendarinertia.feature_calendar.data.local.TaskDao
import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity
import com.valbac.calendarinertia.feature_calendar.domain.repository.TaskRepository
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

    override suspend fun getTaskById(id: Int): TaskEntity? {
        return dao.getTaskById(id)
    }

}