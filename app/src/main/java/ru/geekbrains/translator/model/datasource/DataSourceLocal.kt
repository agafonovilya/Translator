package ru.geekbrains.translator.model.datasource

import ru.geekbrains.translator.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun getData(): T
}
