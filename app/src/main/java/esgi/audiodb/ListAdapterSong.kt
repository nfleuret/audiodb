package esgi.audiodb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.song.Song

class ListAdapterSong(val songs: List<Song>, val context: Context) : RecyclerView.Adapter<ListAdapterSong.ListItemCell>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemCell {

        return ListItemCell(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.title_banner, parent, false)
        )
    }

    override fun onBindViewHolder(cell: ListItemCell, position: Int) {


        var position = cell.adapterPosition


        cell.itemView.findViewById<TextView>(R.id.title_name)
            .setTextBold(
                songs[position].name
            )

        cell.itemView.findViewById<TextView>(R.id.title_number)
            .setTextBold(
                String.format("%d", position + 1)
            )

    }

    override fun getItemCount(): Int {
        return 9;
    }


    class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {

    }
}