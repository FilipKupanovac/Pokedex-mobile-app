package filipkupanovac.pokedex_firered.pokedex.ui.model

data class PokemonCollection(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)