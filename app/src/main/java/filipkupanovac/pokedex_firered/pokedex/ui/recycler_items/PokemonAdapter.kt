package filipkupanovac.pokedex_firered.pokedex.ui.recycler_items

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import filipkupanovac.pokedex_firered.pokedex.R
import filipkupanovac.pokedex_firered.pokedex.databinding.ItemPokemonBinding
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokeObject
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokemonCollection

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    val pokémons = mutableListOf<PokeObject>()
    var pokemonSelectedListener: OnPokemonSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pokemon, parent, false
        )
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokémons[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            pokemonSelectedListener?.OnPokemonSelected(position.toLong())
        }
    }

    override fun getItemCount(): Int = pokémons.count()

    fun setPokemons(pokemons: List<PokeObject>) {
        this.pokémons.clear()
        this.pokémons.addAll(pokemons)
        this.notifyDataSetChanged()
    }
}


class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pokemon: PokeObject) {
        val binding = ItemPokemonBinding.bind(itemView)
        binding.pokemonNameTextView.text = pokemon.name.replaceFirstChar { it.uppercase() }
    }
}
