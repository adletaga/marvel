package com.example.marvel.comic_list.repository

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.marvel.ComicResult
import com.example.marvel.RestApi

class ComicsRepository(val api: RestApi) {

    fun comics(dateRange: String, pageSize: Int): Listing<ComicResult> {

        println("comics $dateRange")
        val sourceFactory = ComicDSFactory(api, dateRange)

        val liveList = sourceFactory.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize * 2
            )

        )
        return Listing(
            liveList,
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            })
    }

}

data class Listing<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit
    // retries any failed requests.

)
