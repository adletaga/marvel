package com.example.marvel.comic_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.marvel.ComicResult
import com.example.marvel.DateUtil

import com.example.marvel.R
import kotlinx.android.synthetic.main.comic_list_fragment.*
import java.util.*

class ComicListFragment : Fragment(), DateFilterDialog.Companion.Listener, ComicClickCallback {

    override fun onClick(id: String?) {
        if (id != null) {
            viewModel.testtest(id)
        } else {
            Log.e(TAG, "ERRROOR to go COMIC  =  NO ID")
        }
    }

    override fun onChange(filter: Filter) {
        viewModel.tryChangeDate(filter)
    }

    companion object {
        val TAG = "ComicListFragment"
        fun newInstance() = ComicListFragment()
    }

    private lateinit var viewModel: ComicListViewModel
    private lateinit var recylcerView: RecyclerView
//    private val viewModel: ComicListViewModel by viewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                val repository = ComicsRepository(RestApi.create())
//                return ComicListViewModel(repository) as T
//            }
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.comic_list_fragment, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ComicListViewModel::class.java)

        initAdapter()
        initAdapter()
        initFilters()
    }

//    fun initCharacters() {
//
//        val glide = Glide.with(this)
//
//        recylcerView = RecyclerView(context!!)
//
//        recylcerView.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        val linearLayoutManager = LinearLayoutManager(context!!)
//        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
//        recylcerView.layoutManager = linearLayoutManager
//
//        val adapter = CharacterAdapter(glide)
//        recylcerView.adapter = adapter
//        viewModel.charactersLD.observe(this, Observer {
//            println("CHACHCHCHCHCHCCHCHCHCCHCHHCHC")
//            adapter.setCharacters(it)
//        })
//    }

    private fun initFilters() {
        viewModel.startDateLD.observe(this, Observer {
            startDate.setText(DateUtil.dateToString(it))
        })

        viewModel.endDateLD.observe(this, Observer {
            endDate.setText(DateUtil.dateToString(it))
        })
        startDate.setOnClickListener {
            showFilterDialog(
                Filter(
                    viewModel.startDateLD.value ?: Date(), true
                )
            )
        }
        endDate.setOnClickListener {
            showFilterDialog(
                Filter(
                    viewModel.endDateLD.value ?: Date(), false
                )
            )
        }

        search.setOnClickListener {
            viewModel.search()
        }
    }

    private fun initAdapter() {
        val adapter = ComicAdapter(this, Glide.with(this))
        list.adapter = adapter

        //todo
        viewModel.comics.observe(this, Observer<PagedList<ComicResult>> {
            adapter.submitList(it)
        })
        viewModel.charactersLD.observe(this, Observer {
            println("CHACHCHCHCHCHCCHCHCHCCHCHHCHC")
            adapter.showCharacters(it)
        })

    }

    private fun showFilterDialog(filter: Filter) {
        val dialogFragment = DateFilterDialog(filter)
        dialogFragment.listener = this
        dialogFragment.show(
            fragmentManager!!,
            dialogFragment::class.simpleName
        )
    }


}
