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

    }
}

fun TextView.setTextBold(text: String, separator: String = ":") {
    val builder = SpannableStringBuilder(text)
    builder.setSpan(StyleSpan(Typeface.BOLD), 0, text.indexOf(separator) + 1, 0)
    setText(builder)
}