package io.pootis.androidlabtsu.service

import dagger.Binds
import dagger.Provides
import io.pootis.androidlabtsu.data.GameResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

const val baseString: String = "uc?id="
const val fileId: String = "1OH9_fchMRHeYyoowCt9TGb9TshHiBtmP"

interface GameApi {
    @GET(baseString + fileId)
    fun getGame(): Deferred<Response<GameResponse>>
}