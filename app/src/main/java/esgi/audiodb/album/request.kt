package esgi.audiodb.album

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import esgi.audiodb.song.Song
import kotlinx.coroutines.Deferred
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.Query

data class ResponseArtist(@SerializedName("artists") val artists: List<Artist>)
data class ResponseAlbums(@SerializedName("album") val albums: List<Album>)
data class ResponseAlbum(@SerializedName("album") val albums: List<Album>)
data class ResponseTrack(@SerializedName("track") val tracks: List<Song>)
data class ResponseSongTrending(@SerializedName("trending") val songs: List<Song>)
data class ResponseAlbumTrending(@SerializedName("trending") val albums: List<Album>)

@Root(name = "GetLyricResult", strict = false)
data class ResponseLyrics constructor(@field:Element(name = "Lyric", required=false) @param:Element(name = "Lyric", required=false) public val Lyric: String?)

interface API {
    @retrofit2.http.GET("artist.php")
    fun getArtistById(@Query("i") artistId: String): Deferred<ResponseArtist>

    @retrofit2.http.GET("search.php")
    fun getArtistInfo(@Query("s") artistName: String): Deferred<ResponseArtist>

    @retrofit2.http.GET("searchalbum.php")
    fun getAlbums(@Query("s") artistName: String): Deferred<ResponseAlbums>

    @retrofit2.http.GET("album.php")
    fun getAlbumById(@Query("m") albumId: String): Deferred<ResponseAlbum>

    @retrofit2.http.GET("track-top10.php")
    fun getMostPopularTracks(@Query("s") artistName: String): Deferred<ResponseTrack>

    @retrofit2.http.GET("track.php")
    fun getTracksByAlbum(@Query("m") albumId: Int): Deferred<ResponseTrack>

    @retrofit2.http.GET("SearchLyricDirect")
    fun getLyrics(@Query("artist") artistName: String, @Query("song") songName: String): Deferred<ResponseLyrics>

    @retrofit2.http.GET("trending.php?country=us&type=itunes&format=singles")
    fun getTrendingsSingles(): Deferred<ResponseSongTrending>

    @retrofit2.http.GET("trending.php?country=us&type=itunes&format=albums")
    fun getTrendingsAlbums(): Deferred<ResponseAlbumTrending>
}

object NetworkManager {

    suspend fun getArtistById(artistId: String): Deferred<ResponseArtist> {
        return getRetrofitFromUrl("https://www.theaudiodb.com/api/v1/json/2/").getArtistById(artistId);
    }

    suspend fun getArtistInfo(artistName: String): Deferred<ResponseArtist> {
        return getRetrofitFromUrl("https://www.theaudiodb.com/api/v1/json/2/").getArtistInfo(artistName);
    }

    suspend fun getAlbums(artistName: String):  Deferred<ResponseAlbums> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getAlbums(artistName)
    }

    suspend fun getAlbumById(albumId: String): Deferred<ResponseAlbum> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getAlbumById(albumId)
    }

    suspend fun getMostPopularTracks(artistName: String): Deferred<ResponseTrack> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getMostPopularTracks(artistName)
    }

    suspend fun getTracksByAlbum(albumId: Int): Deferred<ResponseTrack> {
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getTracksByAlbum(albumId)
    }

    suspend fun getLyrics(artistName: String, songName: String): Deferred<ResponseLyrics> {
        return getRetrofitFromUrlXML("http://api.chartlyrics.com/apiv1.asmx/").getLyrics(artistName.replace(" ", "%20"), songName.replace(" ", "%20"))
    }

    suspend fun getTrendingsSingles() : Deferred<ResponseSongTrending>{
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getTrendingsSingles()
    }

    suspend fun getTrendingsAlbums() : Deferred<ResponseAlbumTrending>{
        return getRetrofitFromUrl("https://theaudiodb.com/api/v1/json/523532/").getTrendingsAlbums()
    }

    fun getRetrofitFromUrl(url: String):  API{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
    }

    fun getRetrofitFromUrlXML(url: String):  API{
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
    }

}