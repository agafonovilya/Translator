package ru.geekbrains.translator

import android.app.Application
import ru.geekbrains.translator.di.AppComponent
import ru.geekbrains.translator.di.DaggerAppComponent

class App: Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

}