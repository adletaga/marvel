package com.example.marvel.comic_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.marvel.DateUtil
import com.example.marvel.comic_list.repository.ComicsRepository
import com.example.marvel.comic_list.repository.Listing
import java.util.*

class ComicListViewModel() : ViewModel() {


    val repository = ComicsRepository(CompicApi.create())

//    private val dateRange = MutableLiveData<String>()


    val repoResult = MutableLiveData<Listing<ComicResult>>()

    private val _comics = Transformations.switchMap(repoResult, { it.pagedList })
    val comics: LiveData<PagedList<ComicResult>> = _comics

    private
    val _startDateLD = MutableLiveData<Date>().apply {
        value = Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 7))
    }
    private val _endDateLD = MutableLiveData<Date>().apply {
        value = Date()
    }

    val startDateLD: LiveData<Date> = _startDateLD
    val endDateLD: LiveData<Date> = _endDateLD

    fun tryChangeDate(filter: Filter) {
        println("tryChangeDate")
    }

    fun search() {
        repoResult.postValue(
            repository.comics(
                "${DateUtil.dateToDateRange(_startDateLD.value!!)}," +
                        "${DateUtil.dateToDateRange(_endDateLD.value!!)}",
                10
            )
        )

    }

    private fun dateChanged(startDate: Date, endDate: Date) {
        _startDateLD.postValue(startDate)
        _endDateLD.postValue(endDate)
    }


}
