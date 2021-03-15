package ru.geekbrains.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}
