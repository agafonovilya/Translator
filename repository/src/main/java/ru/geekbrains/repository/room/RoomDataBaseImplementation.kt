package ru.geekbrains.repository.room

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.dto.SearchResultDto
import ru.geekbrains.repository.convertDataModelSuccessToEntity
import ru.geekbrains.repository.datasource.DataSourceLocal
import ru.geekbrains.repository.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResultDto>> {

    // Возвращаем слово в виде понятного для Activity List<SearchResult>
    override suspend fun getData(word: String): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.getDataByWord(word))
    }

    // Возвращаем список всех слов в виде понятного для Activity List<SearchResult>
    override suspend fun getData(): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.getAll())
    }

    // Метод сохранения слова в БД. Он используется в интеракторе
    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
