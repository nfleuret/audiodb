package esgi.audiodb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.album.Album
import esgi.audiodb.song.Song

class ListAdapterArtist(val songs: List<Song>, val albums: List<Album>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_DESCRIPTION = 0
        const val ITEM_ALBUM = 1
        const val ITEM_TITLE_SONG = 2
        const val ITEM_SONG = 3
    }

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
            return CellViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_banner, null, false)
            )
        }
    }

    override fun onBindViewHolder(cell: RecyclerView.ViewHolder, position: Int) {

        if (cell is TitleViewHolder) {

            var position = cell.adapterPosition


            cell.itemView.findViewById<TextView>(R.id.title_name)
                .setTextBold(
                    songs[position - (albums.size + 2)].name
                )

            cell.itemView.findViewById<TextView>(R.id.title_number)
                .setTextBold(
                    String.format("%d", position - (albums.size + 2))
                )

        } else if (cell is CellViewHolder) {

            var position = cell.adapterPosition

            cell.itemView.findViewById<TextView>(R.id.album_name)
                .setTextBold(
                    albums[position - 1].name
                )
            cell.itemView.findViewById<TextView>(R.id.album_year)
                .setTextBold(
                    albums[position - 1].year.toString()
                )
        }


    }

    override fun getItemCount(): Int {
        return songs.size + albums.size + 1;
    }

    override fun getItemViewType(position: Int): Int {

        if (position == 0) {
            return ITEM_DESCRIPTION
        } else if (position < albums.size + 1) {
            return ITEM_ALBUM
        } else if(position == albums.size + 1) {
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