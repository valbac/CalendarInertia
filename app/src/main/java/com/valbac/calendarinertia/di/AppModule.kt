package com.valbac.calendarinertia.di

import android.app.Application
import androidx.room.Room
import com.valbac.calendarinertia.feature_one.data.local.TheDatabase
import com.valbac.calendarinertia.feature_one.data.repository.TaskRepositoryImpl
import com.valbac.calendarinertia.feature_one.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): TheDatabase {
        return Room.databaseBuilder(
            app,
            TheDatabase::class.java,
            TheDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TheDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }
}
