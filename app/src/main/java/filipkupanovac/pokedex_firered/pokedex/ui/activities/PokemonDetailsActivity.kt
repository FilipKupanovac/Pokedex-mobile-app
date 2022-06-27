package filipkupanovac.pokedex_firered.pokedex.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import filipkupanovac.pokedex_firered.pokedex.R
import filipkupanovac.pokedex_firered.pokedex.databinding.ActivityPokemonDetailsBinding
import filipkupanovac.pokedex_firered.pokedex.helpers.retrieveIconURI
import filipkupanovac.pokedex_firered.pokedex.model.PokeObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModel()

    private var pokemonId: PokeObject = PokeObject("", "")
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtra()
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        detailsViewModel.getSpecificPokemon(id)
        setObservers()
        setOnClickListeners()

        setContentView(binding.root)
    }

    private fun setOnClickListeners() {
        binding.pokemonDetailsFavoriteStar.setOnClickListener {
            detailsViewModel.saveUserFavoriteId(id)
            it.isActivated = !it.isActivated
        }
    }

    private fun setObservers() {
        detailsViewModel.pokemon.observe(this) {
            setContent()
        }
    }

    private fun setContent() {
        //Set name
        binding.namePokemonDetails.text =
            "${detailsViewModel.pokemon.value?.id} ${detailsViewModel.pokemon.value?.name?.replaceFirstChar { it.uppercase() }}"

        //Set types
        val stringBuilder: StringBuilder = java.lang.StringBuilder()
        detailsViewModel.pokemon.value?.types?.forEach { type ->
            stringBuilder.append(type.type.name.replaceFirstChar {
                it.uppercase()
            })
            stringBuilder.append(", ")
        }
        stringBuilder.deleteRange(stringBuilder.count() - 2, stringBuilder.count())
        binding.descriptionPokemonDetails.text = "My Types are: $stringBuilder"

        //Set ImageView
        Glide.with(applicationContext)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${detailsViewModel.pokemon.value?.id}.png")
            .placeholder(
                R.drawable.pokeball_closed
            ).into(binding.imageViewDetailsPokemon)

        //Set type ImageViews
        val typesList: MutableList<String> = mutableListOf()
        detailsViewModel.pokemon.value?.types?.forEach { typeContainer ->
            typesList.add(typeContainer.type.name)
        }
        setTypeImageViews(typesList)

        //Set favorite star
        val star = binding.pokemonDetailsFavoriteStar
        star.isActivated = detailsViewModel.isFavorite(id)
    }

    private fun setTypeImageViews(typesList: List<String>) {

        if (typesList.size == 1) {
            Glide.with(applicationContext).load(retrieveIconURI(typesList[0])).placeholder(null)
                .into(binding.imageViewType1)
            binding.imageViewType2.visibility = View.GONE
        } else {
            Glide.with(applicationContext).load(retrieveIconURI(typesList[0])).placeholder(null)
                .into(binding.imageViewType1)
            Glide.with(applicationContext).load(retrieveIconURI(typesList[1])).placeholder(null)
                .into(binding.imageViewType2)
        }
    }


    private fun getExtra() {
        pokemonId = intent.getSerializableExtra("pokemon") as PokeObject
        val uriArray = pokemonId.url.split('/')
        id = uriArray[uriArray.count() - 2].toInt()
    }

    override fun onBackPressed() {
        finish()
    }
}