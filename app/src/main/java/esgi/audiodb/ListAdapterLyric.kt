package esgi.audiodb

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.song.Song

class ListAdapterLyric(val lyric: List<String>?, val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return LyricCell(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lyrics, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (lyric === null || lyric.size === 0) 1 else lyric.size;
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition

        if(lyric === null || lyric.size === 0) {
            val descriptionNoSong = if(context?.resources !== null) context.resources.getString(R.string.no_song_desc) else ""
            holder.itemView.findViewById<TextView>(R.id.song_lyrics)
                .setTextBold(
                    descriptionNoSong
                )
        }else {
            holder.itemView.findViewById<TextView>(R.id.song_lyrics)
                .setTextBold(
                    lyric[position]
                )
        }
    }
}

class LyricCell(v: View) : RecyclerView.ViewHolder(v) {};