package filipkupanovac.pokedex_firered.pokedex.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokeAPIInstance {

    val api: RetrofitInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
    }
}