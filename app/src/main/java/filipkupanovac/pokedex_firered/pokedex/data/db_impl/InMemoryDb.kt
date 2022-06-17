package filipkupanovac.pokedex_firered.pokedex.data.db_impl

import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon

class InMemoryDb {

    private var pokemons = mutableListOf<Pokemon>()

    init {
        for (i in 1..151) {
            pokemons.add(Pokemon(i.toLong()))
        }
    }

    fun getAllPokemon(): List<Pokemon> = pokemons

    fun getFilteredPokemon(filter: String): List<Pokemon> {
        val tempPokelist = mutableListOf<Pokemon>()

        if (filter.isBlank())
            return pokemons

        for (pokemon in pokemons) {
            if (pokemon.id.toString().contains(filter))
                tempPokelist.add(pokemon)
        }
        return tempPokelist
    }
}