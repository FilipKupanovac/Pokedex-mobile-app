package filipkupanovac.pokedex_firered.pokedex.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filipkupanovac.pokedex_firered.pokedex.data.RetrofitInstance
import filipkupanovac.pokedex_firered.pokedex.ui.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel() : ViewModel() {

    private val _pokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    fun getSpecificPokemon(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                RetrofitInstance.api.getPokemonWithId(id)

            if (response.isSuccessful && response.body() != null){
                _pokemon.postValue(response.body())
            }
        }
    }
}