package com.example.marvel.comic_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import androidx.paging.PagedList
import com.example.marvel.ComicResult
import com.example.marvel.DateUtil

import com.example.marvel.R
import kotlinx.android.synthetic.main.comic_list_fragment.*
import java.util.*

class ComicListFragment : Fragment(), DateFilterDialog.Companion.Listener {
    override fun onChange(filter: Filter) {
        viewModel.tryChangeDate(filter)
    }

    companion object {
        val TAG = "ProductListFragment"
        fun newInstance() = ComicListFragment()
    }

    private lateinit var viewModel: ComicListViewModel
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
        initFilters()
    }

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
        val adapter = ComicAdapter()
        list.adapter = adapter

        //todo
        viewModel.comics.observe(this, Observer<PagedList<ComicResult>> {
            adapter.submitList(it)
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


    fun goToComic(id: String) {

//        fragmentManager?.beginTransaction().addToBackStack("comic").replace()
    }
}
