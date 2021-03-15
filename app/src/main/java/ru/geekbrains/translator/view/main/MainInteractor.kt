package ru.geekbrains.translator.view.main

import ru.geekbrains.model.data.AppState
import ru.geekbrains.repository.Repository
import ru.geekbrains.repository.RepositoryLocal
import ru.geekbrains.core.viewmodel.Interactor
import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.translator.utils.mapSearchResultToResult

class MainInteractor(
        private val remoteRepository: Repository<List<SearchResultDto>>,
        private val localRepository: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(remoteRepository.getData(word)))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(localRepository.getData(word)))
        }
        return appState
    }
}
