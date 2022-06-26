package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.RetrofitInstance
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesToListInt
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import filipkupanovac.pokedex_firered.pokedex.ui.model.PokeObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _pokemonCollection: MutableLiveData<List<PokeObject>> = MutableLiveData()
    val pokemonCollection: LiveData<List<PokeObject>>
        get() = _pokemonCollection

    private val _userFavorites: MutableLiveData<List<Int>> = MutableLiveData()
    val userFavorites: LiveData<List<Int>>
        get() = _userFavorites

    var arePokemonFetched = false
    var areFavoritesFetched = false

    fun getPokemon(count: Int, offset: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                RetrofitInstance.api.getPokemon(count, offset)

            if (response.isSuccessful && response.body() != null) {
                _pokemonCollection.postValue(response.body()!!.pokeList)
                arePokemonFetched = true
            }
        }
    }

    fun saveUserFavoriteId(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.saveFavoritePokemonId(pokemonId)
            prefsManager.saveFavoritePokemonId(pokemonId)
        }
    }

    fun filterPokemons(filter: String): List<PokeObject> {
        val filteredList = pokemonCollection.value?.filter {
            it.name.contains(filter)
        }
        return filteredList!!
    }

    fun saveSearchbarValue(input: String) {
        prefsManager.saveSearchbarValue(input)
    }

    fun getSearchbarValue(): String {
        return prefsManager.getSearchbarValue()
    }

    fun getUserFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.getUserFavorites {
                areFavoritesFetched = true
                _userFavorites.postValue(parseFavoritesToListInt(it))
            }
        }
    }

    companion object {
        val TAG = "PokedexViewModel"
    }
}