package com.example.marvel.comic_list.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marvel.ComicResult
import com.example.marvel.RestApi

class ComicDSFactory(val api: RestApi, val dateRange: String) :
    DataSource.Factory<Int, ComicResult>() {

    val sourceLiveData = MutableLiveData<ComicDataSource>()

    override fun create(): DataSource<Int, ComicResult> {
        val source = ComicDataSource(api, dateRange)
        sourceLiveData.postValue(source)
        return source  // ??
    }

}