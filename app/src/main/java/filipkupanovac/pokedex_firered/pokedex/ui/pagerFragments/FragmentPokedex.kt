package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentPokedexBinding

class FragmentPokedex : Fragment() {

    private lateinit var binding : FragmentPokedexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokedexBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //user specific code
    }
}