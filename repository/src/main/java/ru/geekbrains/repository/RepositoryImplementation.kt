package ru.geekbrains.repository

import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.repository.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResultDto>>) :
    Repository<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}
