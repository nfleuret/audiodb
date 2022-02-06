package esgi.audiodb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.music_main.*

class MusicMainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.music_main,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //link to bottom_nav
        val navHost =
            childFragmentManager.findFragmentById(R.id.music_main_nav_host) as NavHostFragment
        NavigationUI.setupWithNavController(bottom_nav_view, navHost.navController)
    }
}