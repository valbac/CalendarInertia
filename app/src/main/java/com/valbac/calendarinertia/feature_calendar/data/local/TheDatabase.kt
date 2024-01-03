package com.valbac.calendarinertia.feature_calendar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valbac.calendarinertia.feature_calendar.domain.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TheDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object{
        const val DATABASE_NAME = "calendar_db"
    }
}
