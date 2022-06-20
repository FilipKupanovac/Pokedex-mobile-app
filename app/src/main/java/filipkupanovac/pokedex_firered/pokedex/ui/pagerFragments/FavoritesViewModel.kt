package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.RetrofitInstance
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokeObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel() : ViewModel() {
    private val _favoritePokemonsCollection: MutableLiveData<List<PokeObject>> = MutableLiveData()
    val favoritePokemonCollection: LiveData<List<PokeObject>>
        get() = _favoritePokemonsCollection

    fun getFavorites(){
        /* TODO(Implement how to get favorites from room/retrofit, for now just mock) */

        viewModelScope.launch(Dispatchers.IO) {
            val response =
                RetrofitInstance.api.getPokemon(151, 0)

            if(response.isSuccessful && response.body() != null){
                _favoritePokemonsCollection.postValue(response.body()!!.pokeList)
            }
        }
    }

    fun filterPokemons(filter : String) : List<PokeObject>{
        val filteredList = favoritePokemonCollection.value?.filter {
            it.name.contains(filter)
        }
        return filteredList!!
    }

    companion object {
        val TAG = "FavoritesViewModel"
    }
}
