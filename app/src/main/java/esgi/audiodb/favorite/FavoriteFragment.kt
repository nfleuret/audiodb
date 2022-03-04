package esgi.audiodb.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import kotlinx.android.synthetic.main.artist.*
import kotlinx.android.synthetic.main.favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.favorites,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        var artists: List<Artist> = listOf()
        var albums: List<Album> = listOf()

        val databaseManager = context?.let { DatabaseManager(it) }

        GlobalScope.launch(Dispatchers.Default) {
            databaseManager?.listenToAlbumsChanges()?.collect {
                withContext(Dispatchers.Main) {
                    var albumsApp = it.map { element ->  Album("10", element.name, 2012,element.image,"10", "12", "", "" , element.artistName) }
                    albums = albumsApp;
                    favorite_list.adapter = ListAdapter(artists, albums, context);
                }
            }
            databaseManager?.listenToArtistsChanges()?.collect {
                withContext(Dispatchers.Main) {
                    var artistsApp = it.map { element ->  Artist("10", element.name, "", element.image, "") }
                    artists = artistsApp;
                    favorite_list.adapter = ListAdapter(artists, albums, context);
                }
            }
            /*databaseManager?.listenToArtistByName("Eminem")
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
                }*/

            val trendingAlbums = NetworkManager.getTrendingsAlbums().await();
            albums = trendingAlbums.albums

            withContext(Dispatchers.Main) {
                favorite_list.adapter = ListAdapter(artists, albums, context);
            }
        }

        favorite_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(artists, albums, context);
        }
    }
}