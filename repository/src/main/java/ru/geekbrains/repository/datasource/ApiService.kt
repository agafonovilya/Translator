package ru.geekbrains.repository.datasource

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.model.data.dto.SearchResultDto

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<SearchResultDto>>
}
