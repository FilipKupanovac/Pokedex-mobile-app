package filipkupanovac.pokedex_firered.pokedex

import android.app.Application
import filipkupanovac.pokedex_firered.pokedex.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokedexApp : Application() {

    companion object {
        lateinit var application: PokedexApp
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(this@PokedexApp)
            modules(
                listOf(
                    prefsModule,
                    viewModelModule,
                )
            )
        }
    }
}