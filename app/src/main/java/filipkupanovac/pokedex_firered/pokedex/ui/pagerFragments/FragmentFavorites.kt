package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class FragmentFavorites : Fragment(), OnPokemonSelectedListener,
    PokemonAdapter.OnToggleFavoriteClickListener {

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
        favoritesViewModel.loadFavorites()

        setupRecyclerView()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        favoritesViewModel.favoritePokemonCollection.observe(viewLifecycleOwner) {
            updateData()
        }
        favoritesViewModel.areFavoritesLoaded.observe(viewLifecycleOwner) {
            updateData()
        }
    }

    private fun setupRecyclerView() {
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        pokemonAdapter = PokemonAdapter(this, true)
        pokemonAdapter.pokemonSelectedListener = this
        binding.favoritesRecyclerView.adapter = pokemonAdapter
    }

    override fun OnPokemonSelected(id: Int) {
        val intent = Intent(activity, PokemonDetailsActivity::class.java).apply {
            putExtra("pokemon", favoritesViewModel.favoritePokemonCollection.value?.get(id))
        }

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.loadFavorites()
        updateData()

    }

    private fun updateData() {
        if (!favoritesViewModel.favoritePokemonCollection.value.isNullOrEmpty())
            pokemonAdapter.setPokemons(
                favoritesViewModel.favoritePokemonCollection.value!!
            )

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(userVisibleHint){
            favoritesViewModel.getFavorites()
            //favoritesViewModel.loadFavorites()
        }
    }
    override fun onToggleFavoriteClick(favoriteId: Int) {

    }
    private val TAG = "FragmentFavorites"
}