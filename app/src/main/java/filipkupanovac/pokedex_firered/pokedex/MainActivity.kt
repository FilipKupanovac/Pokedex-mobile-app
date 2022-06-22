package filipkupanovac.pokedex_firered.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import filipkupanovac.pokedex_firered.pokedex.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        /*Log.d("ODJEBI", "aaaa ${supportFragmentManager.fragments}")
        Log.d("ODJEBI", "NA STACKU IMA JEBENIH ${supportFragmentManager.backStackEntryCount} FRAGMENATA")
        if (supportFragmentManager.backStackEntryCount > 0) {
            Log.d("DE ODJEBI", "onBackPressed: ")
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }*/

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        //super.onBackPressed()
    }
}