package esgi.audiodb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import esgi.audiodb.artist.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RankingTitleFragment : Fragment() {
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
        Log.w("APIMUSIC", "test");
        GlobalScope.launch(Dispatchers.Main) {
            try {
                Log.w("APIMUSIC", "test2");
                val searchResponse = withContext(Dispatchers.IO)
                { api.search("coldplay").await() }
                //view.updateTodos(todos)
                Log.w("APIMUSIC", searchResponse.toString());
            } catch (e: Exception) {
                e.message?.let { Log.e("APIMUSIC", it) }
            }
        }
    }


}