package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import esgi.audiodb.album.Album
import esgi.audiodb.album.NetworkManager
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArtistsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("on passe dans onCreate", "encore un");
        return inflater.inflate(
            R.layout.artist,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        Log.w("ceci est un message", "encore un");
        val albums: List<Album> = listOf(
            Album("After Hours", 2020),
            Album("Star Boy", 2016),
            Album("Beauty behind the Madness", 2015)
        )
        val songs: List<Song> = listOf(
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness"),
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness"),
            Song("Walk on Water feat.Beyoncé"),
            Song("Star Boy"),
            Song("Beauty behind the Madness")
        )

        GlobalScope.launch(Dispatchers.Default) {
            val response = NetworkManager.getArtistInfo("khaled").await()



            withContext(Dispatchers.Main) {
                name_artist.text = response.artists[0].strArtist;
                artist_localization.text = response.artists[0].strCountry;
                Picasso.get().load(response.artists[0].strArtistThumb).into(image_artist);
                Log.w("pomme", response.artists[0].strArtistThumb);
            }
        }

        album_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapter(albums, context,  object: OnItemClickedListener {
                override fun onItemClicked(album: Album) {
                }
            });
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }

        title_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapterSong(songs, context);
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }


    class ListAdapter(val albums: List<Album>, val context: Context, val listener: OnItemClickedListener) : RecyclerView.Adapter<ListItemCell>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemCell {
            return ListItemCell(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_banner, parent, false)
            )
        }

        override fun onBindViewHolder(cell: ListItemCell, position: Int) {

            var position = cell.adapterPosition

            cell.itemView.findViewById<TextView>(R.id.album_name)
                .setTextBold(
                    albums[position].name
                )
            cell.itemView.findViewById<TextView>(R.id.album_year)
                .setTextBold(
                    albums[position].year.toString()
                )
        }

        override fun getItemCount(): Int {
            return albums.size;
        }

    }

    class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {

    }
}

interface OnItemClickedListener {
    fun onItemClicked(album: Album)
}
