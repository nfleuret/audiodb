package esgi.audiodb.rankings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.ArtistsFragmentDirections
import esgi.audiodb.OnListItemClickListener
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist

class ListAdapterRankingAlbums (
    private val albums : List<Album>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAlbumListItem {
        return RankingAlbumListItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_album_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition
        //set listener
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
                if (view != null && albums != null) {
                    val newArtist = Artist("", albums[position].strArtist, "", "", "", "")
                    view.findNavController().navigate(
                        RankingFragmentDirections.actionRankingFragmentToAlbumFragment(
                            albums[position], newArtist
                        )
                    )
                }
            }
        })

        (holder as RankingAlbumListItem).bindValues(albums[position], position + 1)
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}