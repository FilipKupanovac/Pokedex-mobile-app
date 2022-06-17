package filipkupanovac.pokedex_firered.pokedex.data.db_impl

import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon

class InMemoryDb {

    private var pokemons = mutableListOf<Pokemon>()
    init {
        for (i in 1..151) {
            pokemons.add(Pokemon(i.toLong()))
        }
    }

    public fun getAllPokemon(): List<Pokemon> = pokemons
}