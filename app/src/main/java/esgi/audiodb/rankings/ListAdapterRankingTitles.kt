package esgi.audiodb.rankings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.OnListItemClickListener
import esgi.audiodb.R
import esgi.audiodb.song.Song

class ListAdapterRankingTitles(
    private val songs: List<Song>,
    private val listener: OnListItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingSongListItem {
        return RankingSongListItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_song_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RankingSongListItem).bindValues(songs[position])

        //set listener
        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}