package com.example.webserviceapplication.ui.favorite;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.webserviceapplication.R
import com.example.webserviceapplication.databinding.CardViewMovieItemBinding
import com.example.webserviceapplication.local.model.LocalCharacter
import com.example.webserviceapplication.local.model.LocalMovie
import com.squareup.picasso.Picasso
import kotlin.Unit
import kotlin.collections.ArrayList

class CharactersFavoriteAdapter (
    private val charactersList: ArrayList<LocalCharacter>,
    private val onItemClicked: (LocalCharacter) -> Unit,
    private val onItemLongClicked: (LocalCharacter) -> Unit,
) : RecyclerView.Adapter<CharactersFavoriteAdapter.CharactersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_movie_item, parent, false)
        return CharactersViewHolder(itemView)
    }

    override fun getItemCount(): Int = charactersList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = charactersList[position]
        holder.bindMovie(character)
        holder.itemView.setOnClickListener { onItemClicked(character) }
        holder.itemView.setOnLongClickListener { onItemLongClicked(character)
        true
        }
    }

    fun appendItems(newList: ArrayList<LocalCharacter>){
        charactersList.clear()
        charactersList.addAll(newList)
        notifyDataSetChanged()
    }

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = CardViewMovieItemBinding.bind(itemView)

        fun bindMovie(character: LocalCharacter) {
            with(binding) {
                nameTextView.text = character.name
                speciesTextView.text = character.gender

                Picasso.get().load(character.image).into(pictureImageView)
            }
        }
    }


}
