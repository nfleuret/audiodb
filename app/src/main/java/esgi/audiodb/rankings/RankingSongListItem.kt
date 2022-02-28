package esgi.audiodb.rankings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.ranking_song_list_item.view.*

class RankingSongListItem (v: View) : RecyclerView.ViewHolder(v) {
    fun bindValues(song: Song, number: Int) {
        Picasso.get().load(song.strTrackThumb).into(itemView.album_picture)
        itemView.song_number.text = number.toString()
        itemView.song_name.text = song.strTrack
        itemView.artist_name.text = song.strArtist
    }
}