package ru.geekbrains.repository

import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
