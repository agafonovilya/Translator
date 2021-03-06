package ru.geekbrains.translator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.geekbrains.historyscreen.view.HistoryInteractor
import ru.geekbrains.historyscreen.view.HistoryViewModel
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

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}
