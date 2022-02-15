package esgi.audiodb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.song.Song

class ListAdapterSong(val songs: List<Song>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ListItemCell(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.title_banner, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return songs.size;
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition


        holder.itemView.findViewById<TextView>(R.id.title_name)
            .setTextBold(
                songs[position].strTrack
            )

        holder.itemView.findViewById<TextView>(R.id.title_number)
            .setTextBold(
                String.format("%d", position + 1)
            )
    }
}

class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {};