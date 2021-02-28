package ru.geekbrains.translator.model.repository

import ru.geekbrains.translator.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun getData(): T
}