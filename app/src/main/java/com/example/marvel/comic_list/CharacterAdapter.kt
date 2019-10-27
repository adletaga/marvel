package com.example.marvel.comic_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.marvel.CharacterResult
import com.example.marvel.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CharacterAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<CharacterAdapter.Companion.CharacterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view, glide)
    }

    private var characters: List<CharacterResult>? = null

    fun setCharacters(characters: List<CharacterResult>?) {
        this.characters = characters
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return characters?.size ?: 0
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterResult = characters?.get(position)
        if (characterResult != null) {
            holder.bind(characterResult)
        }
    }

    companion object {
        class CharacterViewHolder(view: View, val glide: RequestManager) :
            RecyclerView.ViewHolder(view) {
            private val title: TextView = view.findViewById(R.id.name)
            private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)

            val job = Job()

            val coroutineContext: CoroutineContext
                get() = job + Dispatchers.IO

            val scope = CoroutineScope(coroutineContext)

            fun bind(character: CharacterResult) {
                println("000000000000000000000000000000000000")
                println("${character.thumbnail.path}.${character.thumbnail.extension}")
                title.setText(character.name)
//                glide.clear(thumbnail)
//                scope.launch {
                glide
                    .load("${character.thumbnail.path}.${character.thumbnail.extension}")
                    .into(thumbnail)
//                }
            }
        }
    }
}