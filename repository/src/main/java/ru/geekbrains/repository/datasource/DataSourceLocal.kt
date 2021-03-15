package ru.geekbrains.repository.datasource

import ru.geekbrains.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun getData(): T
}
