package com.example.webserviceapplication.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webserviceapplication.local.model.LocalCharacter
import com.example.webserviceapplication.local.model.LocalMovie
import com.example.webserviceapplication.local.repository.LocalCharacterRepository
import com.example.webserviceapplication.local.repository.LocalMovieRepository
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val localCharacterRepository = LocalCharacterRepository()

    private val _favoriteCharacters: MutableLiveData<ArrayList<LocalCharacter>> = MutableLiveData()
    val favoriteCharacters: LiveData<ArrayList<LocalCharacter>> = _favoriteCharacters

    fun loadFavoriteCharacters() {
        viewModelScope.launch {
            val listFavoritesCharacters = localCharacterRepository.loadFavoritesCharacter()
            _favoriteCharacters.postValue(listFavoritesCharacters as ArrayList<LocalCharacter>)
        }
    }

    fun deleteFavoriteCharacter(localCharacter: LocalCharacter) {
        viewModelScope.launch {
            localCharacterRepository.deleteFavoriteCharacter(localCharacter)
        }
    }
}