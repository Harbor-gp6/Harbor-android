package com.example.apl_mobile_harbor.classes.application

import android.app.Application
import com.example.apl_mobile_harbor.modulos.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
