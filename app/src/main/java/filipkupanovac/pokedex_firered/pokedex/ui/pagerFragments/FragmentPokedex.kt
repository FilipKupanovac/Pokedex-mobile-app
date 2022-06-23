package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    override fun OnPokemonSelected(id: Int) {
        val pokemonUriArray = pokedexViewModel.pokemonCollection.value?.get(id)?.url?.split('/')
        val pokemonId = pokemonUriArray?.get(pokemonUriArray.size-2)

        val intent = Intent(/*requireActivity()*/activity, PokemonDetailsActivity::class.java).apply {
            //putExtra("id", pokemonId)
            putExtra("pokemon",pokedexViewModel.pokemonCollection.value?.get(id))
        }

        startActivity(intent)
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