package com.valbac.calendarinertia.feature_calendar.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?
}