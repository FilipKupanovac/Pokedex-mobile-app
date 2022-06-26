package filipkupanovac.pokedex_firered.pokedex.ui.recycler_items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import filipkupanovac.pokedex_firered.pokedex.R
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.databinding.ItemPokemonBinding
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesToListInt
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokeObject

class PokemonAdapter(private val isInFavorites: Boolean = false) :
    RecyclerView.Adapter<PokemonViewHolder>() {


    val pokémons = mutableListOf<PokeObject>()
    var pokemonSelectedListener: OnPokemonSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pokemon, parent, false
        )
        return PokemonViewHolder(view, isInFavorites)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokémons[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            pokemonSelectedListener?.OnPokemonSelected(position)
        }
    }

    override fun getItemCount(): Int = pokémons.count()

    fun setPokemons(pokemons: List<PokeObject>) {
        this.pokémons.clear()
        this.pokémons.addAll(pokemons)
        this.notifyDataSetChanged()
    }
}


class PokemonViewHolder(itemView: View, private val isInFavorites: Boolean) :
    RecyclerView.ViewHolder(itemView) {
    val sharedPrefs = SharedPreferenceManager()
    private val binding = ItemPokemonBinding.bind(itemView)
    fun bind(pokemon: PokeObject) {
        binding.pokemonNameTextView.text = pokemon.name.replaceFirstChar { it.uppercase() }

        val pokemonUriArray = pokemon.url.split('/')
        val pokemonId = pokemonUriArray[pokemonUriArray.size - 2].toInt()

        handleFavoritesStar(pokemonId)

    }

    fun handleFavoritesStar(pokemonId: Int) {
        if (isInFavorites) {
            binding.recyclerItemFavoriteStar.visibility = View.GONE
        } else {
            val userFavorites = parseFavoritesToListInt(sharedPrefs.getFavorites())
            var bool = false
            userFavorites.forEach { fav ->
                if (fav == pokemonId)
                    bool = true
            }
            binding.recyclerItemFavoriteStar.isActivated = bool

            binding.recyclerItemFavoriteStar.setOnClickListener {
                toggleFavorite(binding.recyclerItemFavoriteStar, pokemonId)
            }
        }
    }

    private fun toggleFavorite(iv: ImageView, pokemonId: Int) {
        //TODO(POSTAVITI U SHAREDPREFSE I FIREBASE FAVORITE)
        iv.isActivated = !iv.isActivated

    }

}
