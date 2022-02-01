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
import esgi.audiodb.album.Album
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*

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

        val songs: List<Song> = listOf(
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness"),
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness"),
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness")
        )


        title_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterSong(songs, context);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }
}