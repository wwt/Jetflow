package com.wwt.jetflow

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class JetflowApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@JetflowApplication)
        }
        AppModule.init()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}