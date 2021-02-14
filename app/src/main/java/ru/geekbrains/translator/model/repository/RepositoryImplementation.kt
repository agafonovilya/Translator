package ru.geekbrains.translator.model.repository


import io.reactivex.Observable
import ru.geekbrains.translator.model.data.DataModel
import ru.geekbrains.translator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
