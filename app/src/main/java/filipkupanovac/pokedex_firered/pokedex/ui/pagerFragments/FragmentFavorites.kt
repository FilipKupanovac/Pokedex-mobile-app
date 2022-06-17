package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.data.db_impl.InMemoryDb
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentFavoritesBinding
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.OnPokemonSelectedListener
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.PokemonAdapter

class FragmentFavorites : Fragment(), OnPokemonSelectedListener {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter

    private val pokemonDb = InMemoryDb()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(
            inflater, container, false
        )

        setupRecyclerView()

        return binding.root
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
        //setup viewPager page na 0, ažurirati podatke pokemona za details card
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        pokemonAdapter.setPokemons(pokemonDb.getFilteredPokemon("5"))
    }
}