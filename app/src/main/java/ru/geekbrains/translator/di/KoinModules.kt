package ru.geekbrains.translator.di

import androidx.room.Room
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.Repository
import ru.geekbrains.repository.RepositoryImplementation
import ru.geekbrains.repository.RepositoryImplementationLocal
import ru.geekbrains.repository.RepositoryLocal
import ru.geekbrains.repository.datasource.RetrofitImplementation
import ru.geekbrains.repository.room.HistoryDataBase
import ru.geekbrains.repository.room.RoomDataBaseImplementation
import ru.geekbrains.translator.view.main.MainInteractor
import ru.geekbrains.translator.view.main.MainViewModel

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}
val application = module {
    single<Repository<List<DataModel>>> { RepositoryImplementation(
        RetrofitImplementation()
    ) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(
        RoomDataBaseImplementation(get())
    ) }
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}
