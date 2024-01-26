package com.valbac.calendarinertia.di

import android.app.Application
import androidx.room.Room
import com.valbac.calendarinertia.feature_calendar.data.local.TheDatabase
import com.valbac.calendarinertia.feature_calendar.data.remote.PublicHolidayApi
import com.valbac.calendarinertia.feature_calendar.data.repository.PublicHolidaysRepositoryImpl
import com.valbac.calendarinertia.feature_calendar.data.repository.TaskRepositoryImpl
import com.valbac.calendarinertia.feature_calendar.domain.repository.PublicHolidaysRepository
import com.valbac.calendarinertia.feature_calendar.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @Provides
    @Singleton
    fun providePublicHolidayApi(): PublicHolidayApi{
        return Retrofit.Builder()
            .baseUrl("https://date.nager.at/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PublicHolidayApi::class.java)
    }


    @Provides
    @Singleton
    fun providePublicHolidaysRepository(api: PublicHolidayApi): PublicHolidaysRepository {
        return PublicHolidaysRepositoryImpl(api)
    }
}
