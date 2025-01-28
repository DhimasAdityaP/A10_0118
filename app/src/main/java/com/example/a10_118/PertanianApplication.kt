package com.example.a10_118

import android.app.Application
import com.example.a10_118.di.AppContainer
import com.example.a10_118.di.Container

class PertanianApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}