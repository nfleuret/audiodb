package esgi.audiodb.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.rankings.RankingFragmentDirections
import esgi.audiodb.rankings.RankingSongListItem
import esgi.audiodb.song.Song

class ListAdapterFavorite(
    private val artists: List<Artist>,
    private val albums: List<Album>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingSongListItem {
        return RankingSongListItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.album_banner, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition
        var artist : Artist = Artist("111304", "eminem","","", "")
        //set listener
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
//                if (view != null && songs != null) {
//                    view.findNavController().navigate(
//                        RankingFragmentDirections.actionRankingFragment2ToArtistFragment(artist)
//                    )
//                }
            }
        })

        //(holder as RankingSongListItem).bindValues(artist[position])
    }

    override fun getItemCount(): Int {
        return artists.size + albums.size
    }
}