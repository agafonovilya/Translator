package ru.geekbrains.translator.model.repository


import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
