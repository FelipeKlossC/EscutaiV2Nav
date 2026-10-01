package com.example.myapplication.model

data class Playlist(
    val id: Int,
    val nome: String,
    val descricao: String,
    val musicasIds: MutableList<Int> = mutableListOf()
)
