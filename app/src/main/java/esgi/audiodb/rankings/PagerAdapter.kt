package esgi.audiodb.rankings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> RankingAlbumFragment()
            else -> RankingTitleFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2;
    }
}