package esgi.audiodb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import esgi.audiodb.utils.DescriptionLocale
import kotlinx.android.synthetic.main.album.*
import kotlinx.android.synthetic.main.album.ic_fav
import kotlinx.android.synthetic.main.album.ic_fav_off
import kotlinx.android.synthetic.main.album.previous_arrow
import kotlinx.android.synthetic.main.album.ic_grey
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
        println(songs)
        val album = AlbumFragmentArgs.fromBundle(requireArguments()).album
        var artist = AlbumFragmentArgs.fromBundle(requireArguments()).artist

        val databaseManager = context?.let { DatabaseManager(it) }


        GlobalScope.launch {

            databaseManager?.listenToAlbumByName(album.strAlbum)
                ?.collect {
                    println(it)

                    withContext(Dispatchers.Main) {
                        if(it.size === 0) {
                            ic_fav.visibility = View.INVISIBLE
                            ic_fav_off.visibility = View.VISIBLE
                        }else {
                            ic_fav.visibility = View.VISIBLE
                            ic_fav_off.visibility = View.INVISIBLE
                        }
                    }
                }
        }


        GlobalScope.launch(Dispatchers.Default) {
            if(artist.idArtist === "") {
                artist = NetworkManager.getArtistInfo(artist.strArtist).await().artists[0];
            }
            println(album.idAlbum.toInt())
            val songsRequest = NetworkManager.getTracksByAlbum(album.idAlbum.toInt()).await();


            withContext(Dispatchers.Main) {
                songs = songsRequest.tracks;
                album_title.text = album.strAlbum
                artist_name.text = artist.strArtist
                val songsTrad = getString(R.string.songs)
                val votesTrad = getString(R.string.votes)
                album_number_song.text = if(songs === null) "0 $songsTrad" else songs.size.toString() + " $songsTrad";
                Picasso.get().load(album.strAlbumThumb).into(image_album);
                Picasso.get().load(album.strAlbumThumb).into(image_album_min);
                album_mark.text = album.intScore;
                album_number_vote.text = if(album.intScoreVotes !== null) album.intScoreVotes + " $votesTrad" else "0 $votesTrad";
                album_description.text =  DescriptionLocale.getDescriptionOfAlbum(album);
                artist_title.text = getString(R.string.titles)


                ic_fav_off.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.addAlbum(esgi.audiodb.dao.Album(album.strAlbum, album.strAlbumThumb, album.idAlbum, album.intYearReleased, album.intScore, album.intScoreVotes,  album.strDescriptionEN, album.strDescriptionFR, artist.strArtist))
                    }
                }

                ic_grey.setOnClickListener {
                    GlobalScope.launch(Dispatchers.Default) {
                        databaseManager?.deleteAlbum(esgi.audiodb.dao.Album(album.strAlbum, album.strAlbumThumb, album.idAlbum, album.intYearReleased, album.intScore, album.intScoreVotes,  album.strDescriptionEN, album.strDescriptionFR, artist.strArtist))
                    }
                }
                println(songs)
                println(artist)

                title_list.adapter = ListAdapterSong(songs, artist);
            }
        }

        previous_arrow.setOnClickListener {
            requireActivity().onBackPressed()
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