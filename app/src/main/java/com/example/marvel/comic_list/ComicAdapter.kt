package com.example.marvel.comic_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.marvel.CharacterResult
import com.example.marvel.ComicResult
import com.example.marvel.R

class ComicAdapter(val callback: ComicClickCallback, val glide: RequestManager) :
    PagedListAdapter<ComicResult, ComicViewHolder>(POST_COMPARATOR) {

    var test: Pair<String, List<CharacterResult>>? = null
    var hashMap = mutableMapOf<String, List<CharacterResult>?>()

    fun showCharacters(pair: Pair<String, List<CharacterResult>>) {
        println("showCharactersshowCharactersshowCharactersshowCharacters")
        println("")
        test = pair
        hashMap.put(pair.first, pair.second)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val comicViewHolder = ComicViewHolder.create(parent, glide)
//        comicViewHolder.itemView.setOnClickListener { parentView ->
//            println("111111111111111wwwwwwwwwwwwwwwwwwww")
//            parentView.visibility = View.GONE
//            notifyDataSetChanged()
        return comicViewHolder
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            // todo
            if (holder.recylcerView.visibility == View.VISIBLE) {
                holder.recylcerView.visibility = View.GONE
                val id = holder.getComic()?.id
                if (id != null) {
                    hashMap.set(id, null)
                }
            } else {
                holder.recylcerView.visibility = View.VISIBLE
                callback.onClick(holder.getComic()?.id)
                holder.recylcerView.adapter?.notifyDataSetChanged()
            }
        }

        val item = getItem(position)
        val characters = if (item?.id != null) {
            hashMap.get(item.id)
        } else {
            null
        }

        (holder as ComicViewHolder).bind(getItem(position), characters)
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

class ComicViewHolder(view: View, val adapter: CharacterAdapter) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)
    //    val subtitle: TextView = view.findViewById(R.id.subtitle)
    val recylcerView: RecyclerView = view.findViewById(R.id.list)

    private var comic: ComicResult? = null

    fun getComic() = comic

    fun bind(comic: ComicResult?, list: List<CharacterResult>?) {
        this.comic = comic
        title.setText(comic?.title ?: "Нет названия")
        if (list == null) {
            recylcerView.visibility = View.GONE
        } else {
            recylcerView.visibility = View.VISIBLE
        }
        println("p123123123ppppppppppppppppppppppppp")
        println(list?.size)
        adapter.setCharacters(list)
    }

    fun test() {

    }

    companion object {
        fun create(parent: ViewGroup, glide: RequestManager): ComicViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comic_item, parent, false)

            val recylcerView: RecyclerView = view.findViewById(R.id.list)
            val adapter = CharacterAdapter(glide)
            recylcerView.adapter = adapter

            val linearLayoutManager = LinearLayoutManager(recylcerView.context)

            linearLayoutManager.orientation = RecyclerView.HORIZONTAL
            recylcerView.layoutManager = linearLayoutManager

            return ComicViewHolder(view, adapter)
        }
    }

}

interface ComicClickCallback {
    fun onClick(id: String?)
}
