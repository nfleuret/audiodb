package esgi.audiodb

import esgi.audiodb.artist.Artist
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("search.php")
    fun search(@Query("s") query : String) : Deferred<SearchResponse>
}