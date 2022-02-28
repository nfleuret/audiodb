package esgi.audiodb.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.R
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.common.AlbumListItem
import esgi.audiodb.common.ArtistListItem
import kotlinx.android.synthetic.main.song_title.view.*

class ListAdapter(
    private val artists: List<Artist>,
    private val albums: List<Album>,
    private val context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ARTIST_TITLE = 0
        const val ARTIST = 1
        const val ALBUM_TITLE = 2
        const val ALBUM = 3
    }

    private val countArtistDisplay = if (artists.size > 3) 3 else artists.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ARTIST_TITLE -> ArtistTitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_title, parent, false)
            )
            ARTIST -> ArtistListItem(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_banner, parent, false)
            )
            ALBUM_TITLE -> AlbumTitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_title, parent, false)
            )
            else -> {
                AlbumListItem(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.album_banner, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var position = holder.adapterPosition

        if (holder is ArtistTitleViewHolder) {
            holder.itemView.artist_song_most_appreciate.text = context?.resources?.getString(R.string.artists)
        } else if (holder is AlbumTitleViewHolder) {
            holder.itemView.artist_song_most_appreciate.text = context?.resources?.getString(R.string.Albums)
        } else if (holder is ArtistListItem) {
            //set listener
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
//                if (view != null && songs != null) {
//                    view.findNavController().navigate(
//                        RankingFragmentDirections.actionRankingFragment2ToArtistFragment(artist)
//                    )
//                }
                }
            })
            holder.bindValues(artists[position])
        } else {
            var artist: Artist = Artist("111304", "eminem", "", "", "")
            //set listener
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
//                if (view != null && songs != null) {
//                    view.findNavController().navigate(
//                        RankingFragmentDirections.actionRankingFragment2ToArtistFragment(artist)
//                    )
//                }
                }
            })
            (holder as AlbumListItem).bindValues(albums[position])
        }
    }

    override fun getItemCount(): Int {
        return countArtistDisplay + albums.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ARTIST_TITLE
            in 1..countArtistDisplay -> ARTIST
            countArtistDisplay + 1 -> ALBUM_TITLE
            else -> ALBUM
        }
    }
}

class ArtistTitleViewHolder(v: View) : RecyclerView.ViewHolder(v)
class AlbumTitleViewHolder(v: View) : RecyclerView.ViewHolder(v)