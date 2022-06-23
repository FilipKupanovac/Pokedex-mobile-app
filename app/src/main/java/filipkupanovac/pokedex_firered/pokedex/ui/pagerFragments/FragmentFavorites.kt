package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentFavoritesBinding
import filipkupanovac.pokedex_firered.pokedex.ui.activities.PokemonDetailsActivity
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

    override fun OnPokemonSelected(id: Int) {
        val pokemonUriArray = favoritesViewModel.filterPokemons("ol")[id].url.split('/')
        val pokemonId = pokemonUriArray[pokemonUriArray.size-2].toInt()

        val intent = Intent(activity, PokemonDetailsActivity::class.java).apply {
            putExtra("pokemon",favoritesViewModel.favoritePokemonCollection.value?.get(pokemonId-1))
        }

        startActivity(intent)
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