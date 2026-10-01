package com.example.myapplication.model

data class Musica(
    val id: Int,
    val titulo: String,
    val artista: String,
    val album: String,
    val duracaoSegundos: Int,
    val genero: String,
    var isFavorito: Boolean = false,
    var reproducoes: Int = 0
) {
    fun duracaoFormatada(): String {
        val minutos = duracaoSegundos / 60
        val segundos = duracaoSegundos % 60
        return "%d:%02d".format(minutos, segundos)
    }
}
