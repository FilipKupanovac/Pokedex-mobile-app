package filipkupanovac.pokedex_firered.pokedex.ui.pokedexMainHolder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentFavorites
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentPokedex

class MainHolderFragmentAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Pokedex"
            1 -> "Favorites"
            else -> "Ništa to bato"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentPokedex()
            else -> FragmentFavorites()
        }
    }

}