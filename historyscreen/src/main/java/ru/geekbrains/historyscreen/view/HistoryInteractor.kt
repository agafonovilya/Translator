package ru.geekbrains.historyscreen.view

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.repository.Repository
import ru.geekbrains.repository.RepositoryLocal
import ru.geekbrains.translator.utils.mapSearchResultToResult

class HistoryInteractor(
        private val repositoryRemote: Repository<List<SearchResultDto>>,
        private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : IHistoryInteractor<AppState> {

    override suspend fun getData(): AppState {
        return AppState.Success(mapSearchResultToResult(repositoryLocal.getData()))
    }

}

