package esgi.audiodb.rankings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import esgi.audiodb.API
import esgi.audiodb.ListAdapterArtist
import esgi.audiodb.OnListItemClickListener
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.rankings_title.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RankingSongFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.rankings_title,
            container,
            false
        )
    }

    val api = Retrofit.Builder()
        .baseUrl("https://www.theaudiodb.com/api/v1/json/2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        var songs: List<Song> = listOf()

        val databaseManager = context?.let { DatabaseManager(it) }

        //2nd thread
        GlobalScope.launch(Dispatchers.Default) {
            val trendingSongs = NetworkManager.getTrendingsSingles().await();

            songs = trendingSongs.tracks

            //hors recycler view - global
            //main thread
            withContext(Dispatchers.Main) {

                  //2nd render - data fetched from API
                song_list.adapter = ListAdapterRankingSongs(songs);
            }
        }

        //initial render
        song_list.run {
            layoutManager = LinearLayoutManager(context)

            adapter = ListAdapterRankingSongs(songs);
        }
    }


}