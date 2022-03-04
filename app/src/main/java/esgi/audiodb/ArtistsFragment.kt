package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.android.synthetic.main.music_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class ArtistsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("on passe dans onCreate", "encore un");
        return inflater.inflate(
            R.layout.artist,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val navView: BottomNavigationView = bottom_nav_view
        navView.visibility = View.GONE*/
        setHasOptionsMenu(false)
        var albums: List<Album> = listOf()
        var songs: List<Song> = listOf()
        var artist: Artist? = null;
        val databaseManager = context?.let { DatabaseManager(it) }
        Log.w("on est dans on view created", "encore un");
        GlobalScope.launch {
            databaseManager?.listenToArtistByName("Eminem")
                ?.collect {
                    println(it)

                    withContext(Dispatchers.Main) {
                        if(it.size === 0) {
                            ic_fav_off.visibility = View.VISIBLE;
                            ic_fav.visibility = View.INVISIBLE;
                        }else {
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
                        databaseManager?.addArtist(esgi.audiodb.dao.Artist(artists.artists[0].strArtist, artists.artists[0].strArtistThumb))
                    }
                }

                ic_grey.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.deleteArtist(esgi.audiodb.dao.Artist(artists.artists[0].strArtist, artists.artists[0].strArtistThumb))
                    }
                }

                album_list.adapter = ListAdapterArtist(artist, songs, albums);
            }
        }







        album_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterArtist(artist, songs, albums);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }

}
