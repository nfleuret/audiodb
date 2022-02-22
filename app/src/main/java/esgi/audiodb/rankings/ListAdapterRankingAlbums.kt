package esgi.audiodb.rankings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.OnListItemClickListener
import esgi.audiodb.R
import esgi.audiodb.album.Album

class ListAdapterRankingAlbums (
    private val albums : List<Album>,
    private val listener: OnListItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAlbumListItem {
        return RankingAlbumListItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_album_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RankingAlbumListItem).bindValues(albums[position])

        //set listener
        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}