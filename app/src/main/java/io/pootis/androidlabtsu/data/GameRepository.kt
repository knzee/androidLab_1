package io.pootis.androidlabtsu.data

import io.pootis.androidlabtsu.service.GameApi
import javax.inject.Inject

class GameRepository @Inject constructor(private val api : GameApi) : BaseRepository() {

    suspend fun getGame() : MutableList<Game>?{

        val gameResponse = this.safeApiCall(
            call = {api.getGame().await()},
            errorMessage = "Ошибка получения списка игр"
        )

        return gameResponse?.results?.toMutableList()

    }

}