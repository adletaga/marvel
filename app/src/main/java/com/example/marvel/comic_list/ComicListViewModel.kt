package com.example.marvel.comic_list

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.marvel.*
import com.example.marvel.comic_list.repository.ComicsRepository
import com.example.marvel.comic_list.repository.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class ComicListViewModel() : ViewModel() {


    val restApi = RestApi.create()

    val repository = ComicsRepository(restApi)

//    private val dateRange = MutableLiveData<String>()


    val repoResult = MutableLiveData<Listing<ComicResult>>()

    private val _comics = Transformations.switchMap(repoResult, { it.pagedList })
    val comics: LiveData<PagedList<ComicResult>> = _comics

    private val _charactersLD = MutableLiveData<Pair<String, List<CharacterResult>>>()
    val charactersLD: LiveData<Pair<String, List<CharacterResult>>> = _charactersLD

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

    fun testtest(id: String) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                try {
                    val request = restApi.getComic(id)
                    val response = request.execute()
                    println("testtesttesttesttesttesttesttesttesttest $id")
                    val data = response.body()?.data
                    val items = data?.results ?: emptyList()
                    println(items.size)
//                    characters.clear()
//                    characters.addAll(items)
                    _charactersLD.postValue(Pair(id, items))
//            val w = object : Callback<CharacterResponse> {
//                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
//                }
//
//                override fun onResponse(
//                    call: Call<CharacterResponse>,
//                    response: Response<CharacterResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val resp = response.body()
//                        resp?.data
//                    }
//                }
//
//
//            }
                } catch (ioException: IOException) {
                    println("%%%%%%%%%%%%%%%%%%%%%")
                }
            }
        }
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
