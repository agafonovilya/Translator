package ru.geekbrains.historyscreen

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.historyscreen.view.HistoryActivity
import ru.geekbrains.historyscreen.view.HistoryInteractor
import ru.geekbrains.historyscreen.view.HistoryViewModel

fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    scope(named<HistoryActivity>()){
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}
