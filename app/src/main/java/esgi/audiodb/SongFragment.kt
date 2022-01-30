package esgi.audiodb

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esgi.audiodb.song.Song
import kotlinx.android.synthetic.main.artist.*

class SongFragment: Fragment() {
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


        title_list.run {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = ListAdapter(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )
        }
    }


    class ListAdapter(val context: Context) : RecyclerView.Adapter<ListItemCell>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemCell {

            return ListItemCell(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.title_banner, parent, false)
            )
        }

        override fun onBindViewHolder(cell: ListItemCell, position: Int) {

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

            var position = cell.adapterPosition


            cell.itemView.findViewById<TextView>(R.id.title_name)
                .setText(
                    songs[position].name
                )

            cell.itemView.findViewById<TextView>(R.id.title_number)
                .setText(
                    position + 1
                )

        }

        override fun getItemCount(): Int {
            return 9;
        }

    }

    class ListItemCell(v: View) : RecyclerView.ViewHolder(v) {

    }
}
