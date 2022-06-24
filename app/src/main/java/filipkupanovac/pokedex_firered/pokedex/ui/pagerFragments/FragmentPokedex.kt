package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import filipkupanovac.pokedex_firered.pokedex.databinding.FragmentPokedexBinding
import filipkupanovac.pokedex_firered.pokedex.ui.activities.PokemonDetailsActivity
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.OnPokemonSelectedListener
import filipkupanovac.pokedex_firered.pokedex.ui.recycler_items.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPokedex : Fragment(), OnPokemonSelectedListener {

    private val pokedexViewModel: PokedexViewModel by viewModel()
    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun OnPokemonSelected(id: Int) {
        val pokemonUriArray: List<String> = if (binding.pokedexSearchbar.text.isNotBlank()
            && binding.pokedexSearchbar.text.isNotEmpty()
        ) {
            pokedexViewModel.filterPokemons(binding.pokedexSearchbar.text.toString())[id].url.split(
                '/'
            )
        } else {
            pokedexViewModel.pokemonCollection.value?.get(id)?.url?.split('/')!!
        }
        val pokemonId = pokemonUriArray[pokemonUriArray.size - 2].toInt()
        val intent = Intent(activity, PokemonDetailsActivity::class.java).apply {
            putExtra("pokemon", pokedexViewModel.pokemonCollection.value?.get(pokemonId - 1))
        }

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ${pokedexViewModel.getSearchbarValue()}")
        updateData()
    }

    override fun onDestroy() {
        pokedexViewModel.saveSearchbarValue("")
        super.onDestroy()
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
        const val TAG = "FragmentPokedex"
    }
}