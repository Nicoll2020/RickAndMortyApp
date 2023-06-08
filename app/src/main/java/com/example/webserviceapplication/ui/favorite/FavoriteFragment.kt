package com.example.webserviceapplication.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webserviceapplication.R
import com.example.webserviceapplication.databinding.FragmentFavoriteBinding
import com.example.webserviceapplication.local.model.LocalCharacter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?
    ): View {
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteViewModel.loadFavoriteCharacters()

        val favoriteCharactersList = ArrayList<LocalCharacter>()
        val charactersFavoriteAdapter = CharactersFavoriteAdapter(
            favoriteCharactersList,
            onItemClicked = {favoriteCharactersList -> onItemClicked(favoriteCharactersList)},
            onItemLongClicked = {localCharacter ->
                deleteFavoriteCharacter(localCharacter)
                favoriteViewModel.loadFavoriteCharacters()
            })

        binding.moviesFavoriteRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteFragment.requireContext())
        adapter = charactersFavoriteAdapter
        setHasFixedSize(false)
        }

        favoriteViewModel.favoriteCharacters.observe(viewLifecycleOwner){favoriteCharactersList ->
            charactersFavoriteAdapter.appendItems(favoriteCharactersList)
        }

        return binding.root
    }

    private fun deleteFavoriteCharacter(localCharacter: LocalCharacter) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage("Desea Eliminar el personaje ${localCharacter.name} de sus favoritos?")
                setPositiveButton(R.string.accept){ dialog, id ->
                    favoriteViewModel.deleteFavoriteCharacter(localCharacter)
                    favoriteViewModel.loadFavoriteCharacters()
                }
                setNegativeButton(R.string.cancel){dialog, id ->

                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun onItemClicked(localCharacter: LocalCharacter) {
        findNavController().navigate(FavoriteFragmentDirections.actionNavigationFavoriteToDetailLocalFragment(localCharacter = localCharacter))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.loadFavoriteCharacters()
    }
}