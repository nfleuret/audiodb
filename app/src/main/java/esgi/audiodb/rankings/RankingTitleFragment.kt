package esgi.audiodb.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.picasso.Picasso
import esgi.audiodb.API
import esgi.audiodb.ListAdapterArtist
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RankingTitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.rankings_title,
            container,
            false
        )
    }

    val api = Retrofit.Builder()
        .baseUrl("https://www.theaudiodb.com/api/v1/json/2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        var albums: List<Album> = listOf()
        var songs: List<Song> = listOf()
        var artist: esgi.audiodb.album.Artist? = null;
        val databaseManager = context?.let { DatabaseManager(it) }

        GlobalScope.launch {
            databaseManager?.listenToArtistByName("Eminem")
                ?.collect {
                    println(it)

                    withContext(Dispatchers.Main) {
                        if (it.size === 0) {
                            ic_fav_off.visibility = View.VISIBLE;
                            ic_fav.visibility = View.INVISIBLE;
                        } else {
                            ic_fav_off.visibility = View.INVISIBLE;
                            ic_fav.visibility = View.VISIBLE;
                        }
                    }
                }

        }

        GlobalScope.launch(Dispatchers.Default) {
            val artists = NetworkManager.getArtistInfo("eminem").await();
            val mostPopularTitles = NetworkManager.getMostPopularTracks("eminem").await();
            val albumsFromApi = NetworkManager.getAlbums("eminem").await();


            withContext(Dispatchers.Main) {
                name_artist.text = artists.artists[0].strArtist;
                artist_localization.text = artists.artists[0].strCountry;
                Picasso.get().load(artists.artists[0].strArtistThumb).into(image_artist);
                artist = artists.artists[0];
                albums = albumsFromApi.albums;
                songs = mostPopularTitles.tracks;
                ic_fav_off.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.addArtist(
                            esgi.audiodb.dao.Artist(
                                artists.artists[0].strArtist,
                                artists.artists[0].strArtistThumb
                            )
                        )
                    }
                }

                ic_grey.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.deleteArtist(
                            esgi.audiodb.dao.Artist(
                                artists.artists[0].strArtist,
                                artists.artists[0].strArtistThumb
                            )
                        )
                    }
                }

                album_list.adapter = ListAdapterArtist(artist, songs, albums);
            }
        }

//        Log.w("APIMUSIC", "test");
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                Log.w("APIMUSIC", "test2");
//                val searchResponse = withContext(Dispatchers.IO)
//                { api.search("coldplay").await() }
//                //view.updateTodos(todos)
//                Log.w("APIMUSIC", searchResponse.toString());
//            } catch (e: Exception) {
//                e.message?.let { Log.e("APIMUSIC", it) }
//            }
//        }
    }


}