package filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.PokeAPIInstance
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesToListInt
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import filipkupanovac.pokedex_firered.pokedex.model.PokeObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _favoritePokemonsCollection: MutableLiveData<List<PokeObject>> = MutableLiveData()
    val favoritePokemonCollection: LiveData<List<PokeObject>>
        get() = _favoritePokemonsCollection

    private val _areFavoritesLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val areFavoritesLoaded: LiveData<Boolean>
        get() = _areFavoritesLoaded

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                PokeAPIInstance.api.getPokemon(151, 0)

            if (response.isSuccessful && response.body() != null) {
                val favoritesList: MutableList<PokeObject> = mutableListOf()
                firestoreRepository.getUserFavorites {
                    parseFavoritesToListInt(it).forEach { favorite ->
                        favoritesList.add(response.body()!!.pokeList[favorite - 1])
                    }
                    _favoritePokemonsCollection.postValue(favoritesList)
                }
            }
        }
    }

    fun loadFavorites(){
        viewModelScope.launch(Dispatchers.IO){
            firestoreRepository.getUserFavorites {
                prefsManager.saveFavorites(it)
                _areFavoritesLoaded.postValue(true)
            }
        }
    }

}
