package esgi.audiodb.favorite

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import kotlinx.android.synthetic.main.album_banner.view.*
import kotlinx.android.synthetic.main.album_banner.view.album_name
import kotlinx.android.synthetic.main.album_banner.view.album_picture

class FavoriteAlbumListItem(v: View) : RecyclerView.ViewHolder(v) {
    fun bindValues(album: Album) {
        Picasso.get().load(album.strAlbumThumb).into(itemView.album_picture)
        itemView.album_name.text = album.strAlbum
        itemView.album_year.text = album.strArtist
    }
}