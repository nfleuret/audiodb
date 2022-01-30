package esgi.audiodb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.album.Album
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*

class AlbumsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.artist,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        album_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapter(context,  object: OnItemClickedListener {
                override fun onItemClicked(album: Album) {

                }
            })
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }


    class ListAdapter(val context: Context, val listener: OnItemClickedListener) : RecyclerView.Adapter<ListItemCell>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemCell {

            return ListItemCell(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.album_banner, parent, false)
            )
        }

        override fun onBindViewHolder(cell: ListItemCell, position: Int) {

            val albums: List<Album> = listOf(
                Album("After Hours", 2020),
                Album("Star Boy", 2016),
                Album("Beauty behind the Madness", 2015)
            )

            var position = cell.adapterPosition

            cell.itemView.setOnClickListener {
                listener.onItemClicked(albums[position])
            }
            cell.itemView.findViewById<TextView>(R.id.album_name)
                .setText(
                    albums[position].name
                )
            cell.itemView.findViewById<TextView>(R.id.album_year)
                .setText(
                    albums[position].year
                )

        }

        override fun getItemCount(): Int {
            return 3;
        }

    }

    class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {

    }
}

interface OnItemClickedListener {
    fun onItemClicked(album: Album)
}
