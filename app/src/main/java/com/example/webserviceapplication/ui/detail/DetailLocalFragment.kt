package com.example.webserviceapplication.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.webserviceapplication.R
import com.example.webserviceapplication.databinding.FragmentDetailBinding
import com.example.webserviceapplication.databinding.FragmentDetailLocalBinding
import com.squareup.picasso.Picasso

class DetailLocalFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailLocalViewModel: DetailLocalViewModel
    private lateinit var favoriteDetailBinding: FragmentDetailLocalBinding
    private val args: DetailLocalFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailLocalViewModel = ViewModelProvider(this)[DetailLocalViewModel::class.java]
        favoriteDetailBinding = FragmentDetailLocalBinding.inflate(inflater, container, false)

        val favoriteCharacter = args.localCharacter

        favoriteCharacter.id?.let { detailViewModel.searchCharacter(it) }

        val id = favoriteCharacter.id
        val name = favoriteCharacter.name
        val status = favoriteCharacter.status
        val species = favoriteCharacter.species
        val gender = favoriteCharacter.gender
        val image = favoriteCharacter.image
        val type = favoriteCharacter.type
        val episodes = favoriteCharacter.episode

        Log.d("episodios", episodes.toString())

        val pattern = "https://rickandmortyapi.com/api/episode/(\\d+)".toRegex()
        val matches = episodes?.let { pattern.findAll(it) }
        val numbers = matches?.map { it.groupValues[1].toInt() }?.toList()

        val result = numbers?.joinToString(", ")?.replace("[", "")?.replace("]", "")


        with(favoriteDetailBinding){
            nameTextView.text = "Nombre: " + name
            statusDateTextView.text = "Estado: " +status
            speciesTextView.text = "Especie: " +species
            genderTextView.text = "Genero: " +gender
            originTextView.text = "Tipo: " + type

            episodeTextView.text = result.toString()

            Picasso.get().load(image).into(posterImageView)
        }

        setHasOptionsMenu(true)
        return favoriteDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Establecer el título del AppBar
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Detalle Personaje"
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


