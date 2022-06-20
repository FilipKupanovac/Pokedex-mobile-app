package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.data.db_impl.InMemoryDb
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentPokedexBinding
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.OnPokemonSelectedListener
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPokedex : Fragment(), OnPokemonSelectedListener {

    //NOVO
    private val pokedexViewModel: PokedexViewModel by viewModel()

    //END NOVO
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

        pokedexViewModel.getPokemon(151)

        setupRecyclerView()
        setSearchBarListener()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        pokedexViewModel.pokemonCollection.observe(viewLifecycleOwner) {
            updateData()
        }
    }

    private fun setSearchBarListener() {
        binding.pokedexSearchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateData()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        if (binding.pokedexSearchbar.text.isNotBlank()) {
            pokemonAdapter.setPokemons(
                pokedexViewModel.filterPokemons(
                    binding.pokedexSearchbar.text.toString()
                )
            )
        } else {
            if (!pokedexViewModel.pokemonCollection.value.isNullOrEmpty()) {
                pokemonAdapter.setPokemons(pokedexViewModel.pokemonCollection.value!!)
            }
        }
    }

    companion object {
        val TAG = "GGGGG"
    }
}