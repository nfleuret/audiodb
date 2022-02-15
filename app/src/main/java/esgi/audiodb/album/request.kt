package esgi.audiodb.album

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import esgi.audiodb.song.Song
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

data class ResponseArtist(@SerializedName("artists") val artists: List<Artist>)
data class ResponseAlbum(@SerializedName("album") val albums: List<Album>)
data class ResponseTrack(@SerializedName("track") val tracks: List<Song>)

interface API {
    @retrofit2.http.GET("search.php")
    fun getArtistInfo(@Query("s") artistName: String): Deferred<ResponseArtist>

    @retrofit2.http.GET("searchalbum.php")
    fun getAlbums(@Query("s") artistName: String): Deferred<ResponseAlbum>

    @retrofit2.http.GET("track-top10.php")
    fun getMostPopularTracks(@Query("s") artistName: String): Deferred<ResponseTrack>
}

object NetworkManager {


    suspend fun getArtistInfo(artistName: String): Deferred<ResponseArtist> {
        return getRetrofitFromUrl("https://www.theaudiodb.com/api/v1/json/2/").getArtistInfo(artistName);
    }

    suspend fun getAlbums(artistName: String):  Deferred<ResponseAlbum> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getAlbums(artistName)
    }

    suspend fun getMostPopularTracks(artistName: String): Deferred<ResponseTrack> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getMostPopularTracks(artistName)
    }

    fun getRetrofitFromUrl(url: String):  API{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
    }

}