package ru.geekbrains.repository

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.repository.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultDto>>) :
    RepositoryLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }

    override suspend fun getData(): List<SearchResultDto>{
        return dataSource.getData()
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
