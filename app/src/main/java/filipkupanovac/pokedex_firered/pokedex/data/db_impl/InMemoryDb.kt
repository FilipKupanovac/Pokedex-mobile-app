package filipkupanovac.pokedex_firered.pokedex.data.db_impl

import android.util.Log
import androidx.lifecycle.lifecycleScope
import filipkupanovac.pokedex_firered.pokedex.data.RetrofitInstance
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FragmentPokedex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class InMemoryDb {

    private var pokemons = mutableListOf<Pokemon>()

    init {
        //#region TEST

        //lifecycleScope.launchWhenCreated {
            /*val response = try {
                RetrofitInstance.api.getPokemonTEST(13, 12)
            } catch (e: IOException) {
                Log.e(FragmentPokedex.TAG, "onCreate: FUCKEDUP ioe")
                //return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(FragmentPokedex.TAG, "onCreate: FUCKEDUP http")
                //return@launchWhenCreated
            }
        Log.d(TAG, "EVOGA ${response}")*/

            /*if(response.isSuccessful && response.body() != null){
                Log.d(FragmentPokedex.TAG, "POKEMONI ${response.body()}")
            }*/
        //}

        //#endregion

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