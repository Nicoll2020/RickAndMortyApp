package com.example.webserviceapplication.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.webserviceapplication.R
import com.example.webserviceapplication.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var isMovieFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewModel.isMovieFavorite.observe(viewLifecycleOwner){isMovieFavorite ->
            this.isMovieFavorite = isMovieFavorite
            if(isMovieFavorite){
                detailBinding.addFavoriteIcon.setImageDrawable(resources.getDrawable(R.drawable.favoritoicon))
            }else{
                detailBinding.addFavoriteIcon.setImageDrawable(resources.getDrawable(R.drawable.favoritoicon2))
            }
        }

        val character = args.character

        character.id?.let { detailViewModel.searchCharacter(it) }

        with(detailBinding){
            nameTextView.text = character.name
            genderTextView.text = "Genero: " + character.gender
            statusDateTextView.text = "Estado: " + character.status
            speciesTextView.text= "Especie: " + character.species


            val episodeLinks = character.episode?.map { it.toString() }
            val episodeNumbers = episodeLinks?.map { extractEpisodeNumber(it) }
            val episodeNumbersText = episodeNumbers?.joinToString(", ")
            episodeTextView.text = episodeNumbersText




            originTextView.text = character.location?.name.toString()

            Picasso.get().load(character.image).into(posterImageView)

            addFavoriteIcon.setOnClickListener{
                if(isMovieFavorite)
                    Toast.makeText(context, "${character.name} ya esta en tus favoritos", Toast.LENGTH_LONG).show()
                else{
                    isMovieFavorite = true
                    addFavoriteIcon.setImageDrawable(resources.getDrawable(R.drawable.favoritoicon))
                    detailViewModel.saveCharacter(character)
                }
            }
        }

        setHasOptionsMenu(true)
        return detailBinding.root
    }

    fun extractEpisodeNumber(episodeLink: String): String {
        val pattern = "https://rickandmortyapi.com/api/episode/(\\d+)".toRegex()
        val matchResult = pattern.find(episodeLink)
        return matchResult?.groupValues?.getOrNull(1) ?: ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Navegar hacia atrás (al fragmento principal)
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}