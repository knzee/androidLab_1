package io.pootis.androidlabtsu.data

data class Game(
    val name: String,
    val description: String,
    val imgUrl: String
)

data class GameResponse(
    val results: List<Game>
)