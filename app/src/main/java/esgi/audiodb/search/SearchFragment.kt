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
import kotlinx.android.synthetic.main.search.*
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

        GlobalScope.launch(Dispatchers.Default) {
            val artistsFound = NetworkManager.getArtistInfo("eminem").await();
            val albumsFound = NetworkManager.getAlbums("eminem").await();

            artists = artistsFound.artists
            albums = albumsFound.albums

            withContext(Dispatchers.Main) {
                search_list.adapter = ListAdapter(artists, albums, context, "search");
            }
        }

        search_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(artists, albums, context, "search");
        }
    }
}