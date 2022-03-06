package esgi.audiodb.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import esgi.audiodb.API
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.DatabaseManager
import kotlinx.android.synthetic.main.rankings_albums.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RankingAlbumFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.rankings_albums,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        var albums: List<Album> = listOf()

        //2nd thread
        GlobalScope.launch(Dispatchers.Default) {
            val trendingAlbums = NetworkManager.getTrendingsAlbums().await();
            albums = trendingAlbums.albums

            //hors recycler view - global
            //main thread
            withContext(Dispatchers.Main) {

                //2nd render - data fetched from API
                album_list.adapter = ListAdapterRankingAlbums(albums);
            }
        }

        //initial render
        album_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapterRankingAlbums(albums);
        }
    }
}