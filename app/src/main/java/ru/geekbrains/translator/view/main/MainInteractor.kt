package ru.geekbrains.translator.view.main

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.Repository
import ru.geekbrains.repository.RepositoryLocal
import ru.geekbrains.core.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState

    }
}
