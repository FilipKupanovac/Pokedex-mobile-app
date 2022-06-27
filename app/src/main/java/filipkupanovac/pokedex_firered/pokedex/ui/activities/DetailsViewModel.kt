package filipkupanovac.pokedex_firered.pokedex.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.PokeAPIInstance
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.helpers.parseFavoritesToListInt
import filipkupanovac.pokedex_firered.pokedex.repositories.FirestoreRepository
import filipkupanovac.pokedex_firered.pokedex.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val prefsManager: SharedPreferenceManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _pokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun getPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                PokeAPIInstance.api.getPokemonWithId(id)

            if (response.isSuccessful && response.body() != null) {
                _pokemon.postValue(response.body())
            }
        }
    }

    fun saveUserFavoriteId(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.saveFavoritePokemonId(pokemonId)
            prefsManager.saveFavoritePokemonId(pokemonId)
        }
    }

    fun isFavorite(pokemonId: Int): Boolean {
        return parseFavoritesToListInt(prefsManager.getFavorites()).contains(pokemonId)
    }
}