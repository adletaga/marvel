package com.example.marvel.comic_list

data class ComicResponse(
    val code: Int,
    val status: String,
    val data: ComicData
)

data class ComicData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<ComicResult>
)

data class ComicResult(// todo replace
    val id: Int,
    val title: String
)