package esgi.audiodb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.song.Song

class ListAdapterLyric(val lyric: List<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return LyricCell(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lyrics, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (lyric === null) 1 else if (lyric.size === 0 ) 1 else lyric.size;
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition

        if(lyric === null || lyric.size === 0) {
            holder.itemView.findViewById<TextView>(R.id.song_lyrics)
                .setTextBold(
                    "Nous sommes désolé nous ne possédons pas cette chanson dans notre base de donnée, \nsi vous voulez un exemple vous pouvez prendre River de Eminem"
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