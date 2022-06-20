package filipkupanovac.pokedex_firered.pokedex.ui.pokedexMainHolder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentFavorites
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentPokedex
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentPokemonDetails

class MainHolderFragmentAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Details"
            1 -> "Pokedex"
            2 -> "Favorites"
            else -> "Ništa to bato"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentPokemonDetails()
            1 -> FragmentPokedex()
            else -> FragmentFavorites()
        }
    }

}