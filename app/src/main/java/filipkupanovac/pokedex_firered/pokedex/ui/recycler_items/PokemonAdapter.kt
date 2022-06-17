package filipkupanovac.pokedex_firered.pokedex.ui.recycler_items

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import filipkupanovac.pokedex_firered.pokedex.R
import filipkupanovac.pokedex_firered.pokedex.databinding.ItemPokemonBinding
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    val pokémons = mutableListOf<Pokemon>()
    var pokemonSelectedListener : OnPokemonSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pokemon, parent, false
        )
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokémons[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener { pokemonSelectedListener?.OnPokemonSelected(pokemon.id) }
    }

    override fun getItemCount(): Int = pokémons.count()

    fun setPokemons(pokemons: List<Pokemon>) {
        this.pokémons.clear()
        this.pokémons.addAll(pokemons)
        this.notifyDataSetChanged()
    }
}


class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(pokemon : Pokemon){
        val binding = ItemPokemonBinding.bind(itemView)
        //povezati na binding (layout resurs) svaki element i prilagoditi onom što pokemon daje
        binding.pokemonNameTextView.text ="Pokemon ${pokemon.id.toString()}"
    }
}
