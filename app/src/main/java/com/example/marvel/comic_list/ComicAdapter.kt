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

class ComicAdapter : PagedListAdapter<ComicResult, RecyclerView.ViewHolder>(POST_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ComicViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ComicViewHolder).bind(getItem(position))
//        println("onBindViewHolder 1 ")
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        println("onBindViewHolder 2 ")
        super.onBindViewHolder(holder, position, payloads)
    }

//    override fun getItem(position: Int): ComicResult? {
////        println("getItem $position")
//        //todo
//        return null
//    }

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

    init {
        view.setOnClickListener {
            println("setOnClickListenersetOnClickListenersetOnClickListenersetOnClickListener")
            println(comic?.id)
        }
    }


    fun bind(comic: ComicResult?) {
        title.setText(comic?.title ?: "Нет названия")
//        println("bind")
    }

    companion object {
        fun create(parent: ViewGroup): ComicViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_item, parent, false)
            return ComicViewHolder(view)
        }
    }

}
