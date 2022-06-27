package filipkupanovac.pokedex_firered.pokedex.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonCollection(
    @SerializedName("results") var pokeList: List<PokeObject>
) : Serializable

data class PokeObject(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
) : Serializable

data class Pokemon(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("types") var types: List<TypeContainer>,
) : Serializable

data class Type(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
) : Serializable

data class TypeContainer(
    @SerializedName("type") var type: Type
) : Serializable


//https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/2.png
//https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png