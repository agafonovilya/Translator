package ru.geekbrains.translator

import android.app.Application
import org.koin.core.context.startKoin
import ru.geekbrains.translator.di.application
import ru.geekbrains.translator.di.mainScreen

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}