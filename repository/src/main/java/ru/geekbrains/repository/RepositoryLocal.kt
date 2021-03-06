package ru.geekbrains.repository

import ru.geekbrains.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun getData(): T
}