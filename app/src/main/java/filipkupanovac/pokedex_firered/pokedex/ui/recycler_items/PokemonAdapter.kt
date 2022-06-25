package filipkupanovac.pokedex_firered.pokedex.ui.recycler_items

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import filipkupanovac.pokedex_firered.pokedex.R
import filipkupanovac.pokedex_firered.pokedex.databinding.ItemPokemonBinding
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
    val binding = ItemPokemonBinding.bind(itemView)
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
            if ( //TODO(ZAPRAVO POSTAVI if(favoritesArray.contains(pokemonId))
                pokemonId == 2 || pokemonId == 4
            ) {
                binding.recyclerItemFavoriteStar.isActivated = true
            }

            binding.recyclerItemFavoriteStar.setOnClickListener {
                Log.d("HAHA", "bind: HAHA ${pokemonId}")
                toggleFavorite(binding.recyclerItemFavoriteStar)
            }
        }
    }

    private fun toggleFavorite(iv: ImageView) {
        //TODO(POSTAVITI U SHAREDPREFSE I FIREBASE FAVORITE)
        iv.isActivated = !iv.isActivated
    }

}
