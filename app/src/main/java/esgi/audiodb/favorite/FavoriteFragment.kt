package esgi.audiodb.favorite

import android.os.Bundle
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
            databaseManager?.listenToArtistsChanges()?.collect {
                withContext(Dispatchers.Main) {
                    var artistsApp = it.map { artist ->  Artist( artist.artistId, artist.name, artist.country, artist.image, artist.descriptionEn, artist.descriptionFr) }
                    artists = artistsApp;
                    favorite_list.adapter = ListAdapter(artists, albums, context, "favorites");
                }
                databaseManager?.listenToAlbumsChanges()?.collect {
                    withContext(Dispatchers.Main) {
                        var albumsApp = it.map { album ->  Album(album.idAlbum , album.name, album.yearReleased,album.image,album.scoresVote, album.votesNumber, album.descriptionEn, album.descriptionFr, album.artistName) }
                        albums = albumsApp;
                        favorite_list.adapter = ListAdapter(artists, albums, context, "favorites");
                    }
                }
            }


            val trendingAlbums = NetworkManager.getTrendingsAlbums().await();
            albums = trendingAlbums.albums

            withContext(Dispatchers.Main) {
                favorite_list.adapter = ListAdapter(artists, albums, context, "favorites");
            }
        }

        favorite_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(artists, albums, context, "favorites");
        }
    }
}