package com.example.marvel

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
    val id: String,
    val title: String,
    val resourceURI: String,
    var expanded: Boolean = false
)

data class CharacterResponse(
    val code: Int,
    val status: String,
    val data: CharacterData
)

data class CharacterData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterResult>
)

data class CharacterResult(// todo replace
    val id: String,
    val name: String,
    val thumbnail: CharacterTN
)

data class CharacterTN(// todo replace
    val path: String,
    val extension: String
)

