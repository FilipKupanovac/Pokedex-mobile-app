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

class FavoritesViewModel(
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _favoritePokemonsCollection: MutableLiveData<List<PokeObject>> = MutableLiveData()
    val favoritePokemonCollection: LiveData<List<PokeObject>>
        get() = _favoritePokemonsCollection

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                RetrofitInstance.api.getPokemon(151, 0)

            if (response.isSuccessful && response.body() != null) {
                val favoritesList: MutableList<PokeObject> = mutableListOf()
                parseFavoritesToListInt(prefsManager.getFavorites()).forEach { favorite ->
                    favoritesList.add(response.body()!!.pokeList[favorite - 1])
                }
                _favoritePokemonsCollection.postValue(favoritesList)
            }
        }
    }

    companion object {
        val TAG = "FavoritesViewModel"
    }
}
