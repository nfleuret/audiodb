package esgi.audiodb.search

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
import esgi.audiodb.favorite.ListAdapter
import kotlinx.android.synthetic.main.favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.search,
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
            val favoriteAlbums = databaseManager?.listenToAlbumsChanges()
            val favoriteArtists = databaseManager?.listenToArtistsChanges()

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