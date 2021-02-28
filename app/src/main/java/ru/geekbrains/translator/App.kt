package ru.geekbrains.translator

import android.app.Application
import org.koin.core.context.startKoin
import ru.geekbrains.translator.di.application
import ru.geekbrains.translator.di.historyScreen
import ru.geekbrains.translator.di.mainScreen

class App: Application() {
    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}