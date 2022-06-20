package filipkupanovac.pokedex_firered.pokedex.data

import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokemonCollection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("pokemon") //everything coming after baseUrl; endpoint
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): Response<PokemonCollection>


    @GET("pokemon") //everything coming after baseUrl; endpoint
    suspend fun getPokemonTEST(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): Response<PokemonCollection>
}