package esgi.audiodb.album

import com.google.gson.annotations.SerializedName;
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

data class Response(@SerializedName("artists") val artists: List<Artist>)

interface API {
    @retrofit2.http.GET("search.php")
    fun getArtistInfo(@Query("s") artistName: String): Deferred<Response>

    @retrofit2.http.GET("search.php")
    fun getAlbums(@Query("s") artistName: String): Deferred<Response>

    @retrofit2.http.GET("search.php")
    fun getTitles(@Query("s") artistName: String): Deferred<Response>
}

object NetworkManager {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.theaudiodb.com/api/v1/json/2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    suspend fun getArtistInfo(artistName: String): Deferred<Response> {
        return retrofit.getArtistInfo(artistName)
    }

    suspend fun getAlbums(artistNameId: String): Deferred<Response> {
        "https://theaudiodb.com/api/v1/json/2/album.php?i=112024"
        return retrofit.getArtistInfo(artistNameId)
    }

    suspend fun getTitles(artistNameId: String): Deferred<Response> {
        "https://theaudiodb.com/api/v1/json/2/track.php?m=2115888"
        return retrofit.getArtistInfo(artistNameId)
    }

}