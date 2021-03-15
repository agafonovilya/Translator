package ru.geekbrains.repository.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}
