package filipkupanovac.pokedex_firered.pokedex.di

import filipkupanovac.pokedex_firered.pokedex.ui.userAuth.RegisterViewModel
import filipkupanovac.pokedex_firered.pokedex.ui.userAuth.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignInViewModel() }
    viewModel { RegisterViewModel() }
}