package filipkupanovac.pokedex_firered.pokedex.di

import com.google.firebase.auth.FirebaseAuth
import filipkupanovac.pokedex_firered.pokedex.data.SharedPreferenceManager
import filipkupanovac.pokedex_firered.pokedex.repositories.FirebaseAuthRepository
import filipkupanovac.pokedex_firered.pokedex.ui.activities.DetailsViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.FavoritesViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.pagerFragments.PokedexViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.profileInfo.ProfileInfoViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.userAuth.RegisterViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.userAuth.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val prefsModule = module {
    single<SharedPreferenceManager> { SharedPreferenceManager() }
    single { FirebaseAuthRepository(FirebaseAuth.getInstance()) }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { PokedexViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { ProfileInfoViewModel(get()) }
}