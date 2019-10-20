package com.example.marvel.comic_list.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marvel.comic_list.ComicResult
import com.example.marvel.comic_list.CompicApi

class ComicDSFactory(val api: CompicApi, val dateRange: String) :
    DataSource.Factory<Int, ComicResult>() {

    val sourceLiveData = MutableLiveData<ComicDataSource>()

    override fun create(): DataSource<Int, ComicResult> {
        val source = ComicDataSource(api, dateRange)
        sourceLiveData.postValue(source)
        return source  // ??
    }

}