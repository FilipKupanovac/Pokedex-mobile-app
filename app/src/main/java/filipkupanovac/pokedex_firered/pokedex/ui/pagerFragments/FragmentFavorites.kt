package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.data.db_impl.InMemoryDb
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentFavoritesBinding
import filipkupanovac.pokedex_firered.pokedex.ui.PokemonDetailsActivity
import filipkupanovac.pokedex_firered.pokedex.ui.pokedexMainHolder.FragmentMainHolderDirections
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.OnPokemonSelectedListener
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavorites : Fragment(), OnPokemonSelectedListener {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(
            inflater, container, false
        )

        favoritesViewModel.getFavorites()

        setupRecyclerView()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        favoritesViewModel.favoritePokemonCollection.observe(viewLifecycleOwner) {
            updateData()
        }
    }

    private fun setupRecyclerView() {
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        pokemonAdapter = PokemonAdapter()
        pokemonAdapter.pokemonSelectedListener = this
        binding.favoritesRecyclerView.adapter = pokemonAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun OnPokemonSelected(id: Long?) {
        //TODO(setup viewPager page na 0, ažurirati podatke pokemona za details card)
        if (id != null) {
            Log.d(
                FragmentPokedex.TAG,
                "OnPokemonSelected: ${favoritesViewModel.filterPokemons("ol")[id.toInt()]}"
            )
        }
        val intent = Intent(/*requireContext()*/requireActivity(), PokemonDetailsActivity::class.java)

        startActivity(intent)
        /*findNavController().navigate(FragmentMainHolderDirections.actionFragmentMainHolderToFragmentProfileInfo(1L))*/
    }

    override fun onResume() {
        super.onResume()
        updateData()

    }

    private fun updateData() {
        if (!favoritesViewModel.favoritePokemonCollection.value.isNullOrEmpty())
            pokemonAdapter.setPokemons(
                favoritesViewModel.filterPokemons("ol")
            )
    }
}