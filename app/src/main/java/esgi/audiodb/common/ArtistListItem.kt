package esgi.audiodb.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.Artist
import kotlinx.android.synthetic.main.album_banner.view.*

class ArtistListItem(v: View) : RecyclerView.ViewHolder(v) {
    fun bindValues(artist: Artist) {
        Picasso.get().load(artist.strArtistThumb).into(itemView.album_picture)
        itemView.album_name.text = artist.strArtist
    }
}