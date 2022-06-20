package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.RetrofitInstance
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokeObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {

    private val _pokemonCollection: MutableLiveData<List<PokeObject>> = MutableLiveData()
    val pokemonCollection: LiveData<List<PokeObject>>
        get() = _pokemonCollection

    fun getPokemon(count: Int, offset: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                RetrofitInstance.api.getPokemon(count, offset)

            if(response.isSuccessful && response.body() != null){
                _pokemonCollection.postValue(response.body()!!.pokeList)
            }
        }
    }

    fun filterPokemons(filter : String) : List<PokeObject>{
        val filteredList = pokemonCollection.value?.filter {
            it.name.contains(filter)
        }
        return filteredList!!
    }

    companion object {
        val TAG = "PokedexViewModel"
    }
}