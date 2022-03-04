package esgi.audiodb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.album.Artist
import esgi.audiodb.song.Song

class ListAdapterSong(val songs: List<Song>, val artist: Artist) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {

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

        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                if (p0 != null && artist != null && artist.idArtist !== "") {
                    p0.findNavController().navigate(
                        AlbumFragmentDirections.actionAlbumFragmentToSongFragment(
                            songs[position], artist
                        )
                    )
                }
            }
        })


        holder.itemView.findViewById<TextView>(R.id.title_name)
            .setTextBold(
                songs[position].strTrack
            )

        holder.itemView.findViewById<TextView>(R.id.title_number)
            .setTextBold(
                String.format("%d", position + 1)
            )
    }

    override fun onClick(p0: View?) {

    }
}

class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {};