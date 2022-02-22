package esgi.audiodb

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.dao.Album
import esgi.audiodb.dao.Artist
import esgi.audiodb.dao.DatabaseManager
import kotlinx.android.synthetic.main.artist.*
import kotlinx.android.synthetic.main.song.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remy_activity_main)
        supportActionBar?.hide()

        val databaseManager = DatabaseManager(this@MainActivity)

        GlobalScope.launch {
            databaseManager.addAlbum(
                Album(
                    name = "toto",
                    image = "http://lolilol",
                    artistName = "jean"
                )
            )
            databaseManager.addArtist(
                Artist(
                    name = "toto",
                    image = "http://lolilol",
                )
            )
        }

        GlobalScope.launch {
            println(databaseManager.listenToAlbumsChanges())

        }
    }
}

fun TextView.setTextBold(text: String, separator: String = ":") {
    val builder = SpannableStringBuilder(text)
    builder.setSpan(StyleSpan(Typeface.BOLD), 0, text.indexOf(separator) + 1, 0)
    setText(builder)
}