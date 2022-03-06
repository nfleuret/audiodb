package esgi.audiodb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.android.synthetic.main.artist.ic_fav
import kotlinx.android.synthetic.main.artist.ic_fav_off
import kotlinx.android.synthetic.main.artist.ic_grey
import kotlinx.android.synthetic.main.artist.previous_arrow
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
        var artist: Artist = ArtistsFragmentArgs.fromBundle(requireArguments()).artist;
        val databaseManager = context?.let { DatabaseManager(it) }
        GlobalScope.launch {
            databaseManager?.listenToArtistByName(artist.strArtist)
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
            if(artist.idArtist === "") {
                artist = NetworkManager.getArtistInfo(artist.strArtist).await().artists[0];
            }
            //val artists = NetworkManager.getArtistInfo("eminem").await();
            val mostPopularTitles = NetworkManager.getMostPopularTracks(artist.strArtist).await();
            val albumsFromApi = NetworkManager.getAlbums(artist.strArtist).await();


            withContext(Dispatchers.Main) {
                name_artist.text = artist.strArtist;
                artist_localization.text = artist.strCountry;
                Picasso.get().load(artist.strArtistThumb).into(image_artist);
                albums = albumsFromApi.albums;
                songs = mostPopularTitles.tracks;
                if(songs === null){
                    songs = listOf()
                }
                ic_fav_off.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.addArtist(esgi.audiodb.dao.Artist(artist.strArtist, artist.strArtistThumb, artist.strBiographyEN, artist.strBiographyFR, artist.strCountry, artist.idArtist))
                    }
                }

                ic_grey.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.deleteArtist(esgi.audiodb.dao.Artist(artist.strArtist, artist.strArtistThumb, artist.strBiographyEN, artist.strBiographyFR, artist.strCountry, artist.idArtist))
                    }
                }

                album_list.adapter = ListAdapterArtist(artist, songs, albums, context);
            }
        }

        previous_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }


        val firstArtistPassed = if (artist.idArtist === "") null else artist
        album_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterArtist(firstArtistPassed, songs, albums, context);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }

}
