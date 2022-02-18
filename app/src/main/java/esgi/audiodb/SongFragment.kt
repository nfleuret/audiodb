package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.android.synthetic.main.song.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        GlobalScope.launch(Dispatchers.Default) {
            val lyrics = NetworkManager.getLyrics("eminem", "River").await();
            val lyricsWords = lyrics.Lyric.replace("\n"," ").split(" ");

            val LyricsPast: MutableList<String> = mutableListOf();
            var LyricsForOneOccurence: String = "";
            var indice = 1;
            val pattern = "\\.".toRegex();

            lyricsWords.forEach { lyricWord ->
                val isTheEndOfASentence = pattern.containsMatchIn(lyricWord);
                LyricsForOneOccurence += "$lyricWord "
                if(indice % 40 == 0 && isTheEndOfASentence) {
                    LyricsPast.add(LyricsForOneOccurence);
                    LyricsForOneOccurence = "";
                }

                if(indice % 40 != 0 || isTheEndOfASentence) indice += 1
            }

            withContext(Dispatchers.Main) {
                lyrics_recycler.adapter = ListAdapterLyric(LyricsPast);
            }
        }

        lyrics_recycler.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterLyric(null);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }
}

