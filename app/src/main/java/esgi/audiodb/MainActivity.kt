package esgi.audiodb

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import esgi.audiodb.dao.Artist
import esgi.audiodb.dao.DatabaseManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remy_activity_main)
        supportActionBar?.hide()

        /*GlobalScope.launch(Dispatchers.Default) {
            val response = NetworkManager.getArtistInfo("coldplay")

            Log.w("reponse", response.toString());
        }*/

        val databaseManager = DatabaseManager(this@MainActivity)

        GlobalScope.launch {
            databaseManager.listenToArtistsChanges()
                .collect {
                    print(it)
                }
        }

        GlobalScope.launch {
            databaseManager.addAnimal(
                Artist(
                    name = "toto",
                    image = "http://lolilol",
                )
            )
        }

        /*findViewById<Button>(R.id).setOnClickListener {

        }*/
    }
}

fun TextView.setTextBold(text: String, separator: String = ":") {
    val builder = SpannableStringBuilder(text)
    builder.setSpan(StyleSpan(Typeface.BOLD), 0, text.indexOf(separator) + 1, 0)
    setText(builder)
}