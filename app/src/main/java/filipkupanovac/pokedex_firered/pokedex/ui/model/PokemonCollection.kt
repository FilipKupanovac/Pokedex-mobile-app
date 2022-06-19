package filipkupanovac.pokedex_firered.pokedex.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonCollection(
    @SerializedName("results") var pokeList: List<PokeObject>
) : Serializable

data class PokeObject(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
) : Serializable
