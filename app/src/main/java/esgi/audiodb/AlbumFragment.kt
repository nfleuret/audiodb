package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.album.*
import kotlinx.android.synthetic.main.artist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.album,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //val album = Album("Revival",2017,"https://www.theaudiodb.com/images/media/album/thumb/twsyqy1513337658.jpg")

        var songs: List<Song> = listOf();
        val album = AlbumFragmentArgs.fromBundle(requireArguments()).album
        val artist = AlbumFragmentArgs.fromBundle(requireArguments()).artist



        GlobalScope.launch(Dispatchers.Default) {
            val databaseManager = context?.let { DatabaseManager(it) }
            println(databaseManager?.listAlbum())

            val songsRequest = NetworkManager.getTracksByAlbum(album.idAlbum.toInt()).await();

            withContext(Dispatchers.Main) {
                songs = songsRequest.tracks;
                album_title.text = album.strAlbum
                album_number_song.text = songs.size.toString() + " chansons";
                Picasso.get().load(album.strAlbumThumb).into(image_album);
                Picasso.get().load(album.strAlbumThumb).into(image_album_min);
                album_mark.text = album.intScore;
                album_number_vote.text = album.intScoreVotes + " votes";


                    title_list.adapter = ListAdapterSong(songs, artist);
            }
        }


        title_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterSong(songs, artist);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }
}