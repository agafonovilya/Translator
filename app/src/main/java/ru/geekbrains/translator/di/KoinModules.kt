package ru.geekbrains.translator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.geekbrains.translator.App
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.datasource.RetrofitImplementation
import ru.geekbrains.translator.model.room.RoomDataBaseImplementation
import ru.geekbrains.translator.model.repository.Repository
import ru.geekbrains.translator.model.repository.RepositoryImplementation
import ru.geekbrains.translator.model.repository.RepositoryImplementationLocal
import ru.geekbrains.translator.model.repository.RepositoryLocal
import ru.geekbrains.translator.model.room.HistoryDataBase
import ru.geekbrains.translator.viewmodel.history.HistoryInteractor
import ru.geekbrains.translator.viewmodel.history.HistoryViewModel
import ru.geekbrains.translator.viewmodel.main.MainInteractor
import ru.geekbrains.translator.viewmodel.main.MainViewModel

val application = module {
    single<Repository<List<DataModel>>> { RepositoryImplementation(
        RetrofitImplementation()
    ) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(
        RoomDataBaseImplementation(get())
    ) }
    single { Room.databaseBuilder(App.instance, HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}
