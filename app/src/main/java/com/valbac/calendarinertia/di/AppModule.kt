package com.valbac.calendarinertia.di

import android.content.Context
import androidx.room.Room
import com.valbac.calendarinertia.feature_one.data.local.TheDatabase
import com.valbac.calendarinertia.feature_one.data.repository.TaskRepositoryImpl
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository

interface AppModule {
    val db: TheDatabase
    val taskRepository: TaskRepository
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val db: TheDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            TheDatabase::class.java,
            TheDatabase.DATABASE_NAME
        ).build()
    }
    override val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(db.taskDao)
    }
}
