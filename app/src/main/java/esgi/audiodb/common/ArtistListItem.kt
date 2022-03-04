package esgi.audiodb.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Artist
import kotlinx.android.synthetic.main.artist_banner.view.*
import kotlinx.android.synthetic.main.ranking_album_list_item.view.artist_name

class ArtistListItem(v: View) : RecyclerView.ViewHolder(v) {
    fun bindValues(artist: Artist) {
        Picasso.get().load(artist.strArtistThumb).into(itemView.artist_picture)
        itemView.artist_name.text = artist.strArtist
    }
}