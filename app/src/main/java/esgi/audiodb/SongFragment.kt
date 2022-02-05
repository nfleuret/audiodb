package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import esgi.audiodb.album.Album
import esgi.audiodb.song.Song

class SongFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("on passe dans onCreate", "encore un");
        return inflater.inflate(
            R.layout.song,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        Log.w("ceci est un message", "encore un");
        val albums: List<Album> = listOf(
            Album("After Hours", 2020),
            Album("Star Boy", 2016),
            Album("Beauty behind the Madness", 2015)
        )
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
    }
}