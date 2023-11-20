package com.valbac.calendarinertia

import android.app.Application
import com.valbac.calendarinertia.di.AppModule
import com.valbac.calendarinertia.di.AppModuleImpl

class CalendarInertiaApplication: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}