package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.coroutines.*


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
        setHasOptionsMenu(true)
        Log.w("ceci est un message", "encore un");
        var albums: List<Album> = listOf()
        var songs: List<Song> = listOf()
        var artist: Artist? = null;

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
