package esgi.audiodb.rankings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.ArtistsFragmentDirections
import esgi.audiodb.OnListItemClickListener
import esgi.audiodb.R
import esgi.audiodb.song.Song

class ListAdapterRankingSongs(
    private val songs: List<Song>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingSongListItem {
        return RankingSongListItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_song_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition
        //set listener
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
//                if (view != null && artist != null) {
//                    view.findNavController().navigate(
//                        ArtistsFragmentDirections.actionArtistFragmentToSongFragment(
//                            songs[position - (countAlbumDisplay + 2)], artist
//                        )
//                    )
//                }
            }
        })

        (holder as RankingSongListItem).bindValues(songs[position], position + 1)
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}