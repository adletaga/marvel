package com.example.marvel.comic_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.ComicResult
import com.example.marvel.R

class ComicAdapter(val callback: ComicClickCallback) :
    PagedListAdapter<ComicResult, RecyclerView.ViewHolder>(POST_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val comicViewHolder = ComicViewHolder.create(parent)
        comicViewHolder.itemView.setOnClickListener {
            callback.onClick(comicViewHolder.getComic()?.id)
        }
        return comicViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ComicViewHolder).bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    companion object {


        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ComicResult>() {
            override fun areItemsTheSame(oldItem: ComicResult, newItem: ComicResult): Boolean {
//                todo
                return true
            }

            override fun areContentsTheSame(oldItem: ComicResult, newItem: ComicResult): Boolean {
//                todo
                return true
            }

        }
    }
}

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)
    private var comic: ComicResult? = null

    fun getComic() = comic

    fun bind(comic: ComicResult?) {
        this.comic = comic
        title.setText(comic?.title ?: "Нет названия")
    }

    companion object {
        fun create(parent: ViewGroup): ComicViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_item, parent, false)
            return ComicViewHolder(view)
        }
    }

}

interface ComicClickCallback {
    fun onClick(id: String?)
}
