package esgi.audiodb.rankings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import kotlinx.android.synthetic.main.ranking_album_list_item.view.*

class RankingAlbumListItem (v: View) : RecyclerView.ViewHolder(v) {
    fun bindValues(album: Album, number: Int) {
        Picasso.get().load(album.strAlbumThumb).into(itemView.album_picture)
        itemView.song_number.text = number.toString()
        itemView.album_name.text = album.strAlbum
        itemView.artist_name.text = album.strArtist
    }
}