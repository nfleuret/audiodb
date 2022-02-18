package esgi.audiodb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import esgi.audiodb.song.Song
import kotlinx.coroutines.NonDisposableHandle.parent

class ListAdapterArtist(val artist: Artist?, val songs: List<Song>, val albums: List<Album>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_DESCRIPTION = 0
        const val ITEM_ALBUM = 1
        const val ITEM_TITLE_SONG = 2
        const val ITEM_SONG = 3
    }

    val countAlbumDisplay = if(albums.size > 0) 3 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_TITLE_SONG) {
            return TitleSongViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_title, null, false)
            )
        } else if(viewType == ITEM_DESCRIPTION) {
            return DescriptionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.artist_description, null, false)
            )
        }else if (viewType == ITEM_SONG) {
            return TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.title_banner, null, false)
            )
        } else {
            val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_banner, null, false)

            return CellViewHolder(v);
        }
    }

    override fun onBindViewHolder(cell: RecyclerView.ViewHolder, position: Int) {
        if (cell is TitleViewHolder) {

            var position = cell.adapterPosition


            cell.itemView.findViewById<TextView>(R.id.title_name)
                .setTextBold(
                    songs[position - (countAlbumDisplay + 2)].strTrack
                )

            cell.itemView.findViewById<TextView>(R.id.title_number)
                .setTextBold(
                    String.format("%d", position - (countAlbumDisplay + 2))
                )

        } else if (cell is CellViewHolder) {

            cell.itemView.setOnClickListener(object: View.OnClickListener {
                override fun onClick(p0: View?) {
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToNavBarFragment(
                            product
                        )
                    )
                }
            })

            var position = cell.adapterPosition

            cell.itemView.findViewById<TextView>(R.id.album_name)
                .setTextBold(
                    albums[position - 1].strAlbum
                )
            cell.itemView.findViewById<TextView>(R.id.album_year)
                .setTextBold(
                    albums[position - 1].intYearReleased.toString()
                )
        }else if (cell is DescriptionViewHolder) {

            if (artist != null) {
                cell.itemView.findViewById<TextView>(R.id.artist_description)
                    .setTextBold(
                        artist.strBiographyEN
                    )
            }

            cell.itemView.findViewById<TextView>(R.id.artist_album_title)
                .setTextBold(
                    "Albums(" + albums.size + ")"
                )
        }


    }

    override fun getItemCount(): Int {
        return songs.size + countAlbumDisplay + 1;
    }

    override fun getItemViewType(position: Int): Int {

        if (position == 0) {
            return ITEM_DESCRIPTION
        } else if (position < countAlbumDisplay + 1) {
            return ITEM_ALBUM
        } else if(position == countAlbumDisplay + 1) {
            return ITEM_TITLE_SONG
        } else {
            return ITEM_SONG
        }
    }
}
class TitleSongViewHolder(v: View) : RecyclerView.ViewHolder(v)
class DescriptionViewHolder(v: View) : RecyclerView.ViewHolder(v)
class TitleViewHolder(v: View) : RecyclerView.ViewHolder(v)
class CellViewHolder(v: View) : RecyclerView.ViewHolder(v)