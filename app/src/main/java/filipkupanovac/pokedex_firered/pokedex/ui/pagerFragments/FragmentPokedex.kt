package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.data.db_impl.InMemoryDb
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentPokedexBinding
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.OnPokemonSelectedListener
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.PokemonAdapter

class FragmentPokedex : Fragment(), OnPokemonSelectedListener {

    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter

    private val pokemonDb = InMemoryDb()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexBinding.inflate(
            inflater, container, false
        )

        setupRecyclerView()

        return binding.root
    }


    private fun setupRecyclerView() {
        binding.pokedexRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        pokemonAdapter = PokemonAdapter()
        pokemonAdapter.pokemonSelectedListener = this
        binding.pokedexRecyclerView.adapter = pokemonAdapter
    }

    override fun OnPokemonSelected(id: Long?) {
        //setup viewPager page na 0, ažurirati podatke pokemona za details card
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        pokemonAdapter.setPokemons(pokemonDb.getAllPokemon())
    }

}