package com.example.marvel.comic_list.repository

import androidx.paging.PositionalDataSource
import com.example.marvel.comic_list.ComicResult
import com.example.marvel.comic_list.CompicApi
import java.io.IOException

class ComicDataSource(val api: CompicApi, val dateRange: String) :
    PositionalDataSource<ComicResult>() {


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ComicResult>) {
        val request = api.getTopAfter(
            limit = params.loadSize,
            dateRange = dateRange,
            offset = params.startPosition
        )
//        todo show douwloading
        try {
            println("1111111111loadRange")
            val response = request.execute()
            val data = response.body()?.data
            val items = data?.results ?: emptyList()
            println(items.size)
            callback.onResult(items)
        } catch (ioException: IOException) {
        }

    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<ComicResult>
    ) {

        params.requestedLoadSize

        println("wwwwwwwwwwwwwwwwwwwwwwwwwwwww")
        val request = api.getTop(
            limit = params.requestedLoadSize,
            dateRange = dateRange
        )
//        todo show douwloading
        try {
            val response = request.execute()
            println("1111111111loadInitial")
            val data = response.body()?.data
            val items = data?.results ?: emptyList()
            println(items.size)
            callback.onResult(items, items.count())
        } catch (ioException: IOException) {
            println("22222222")
        }
    }


//    override fun loadInitial(
//        params: LoadInitialParams<String>,
//        callback: LoadInitialCallback<String, ComicResult>
//    ) {

//    }
//
//    override fun loadAfter(
//        params: LoadParams<String>,
//        callback: LoadCallback<String, ComicResult>
//    ) {
//    }
//
//    override fun loadBefore(
//        params: LoadParams<String>,
//        callback: LoadCallback<String, ComicResult>
//    ) {
//    }

}