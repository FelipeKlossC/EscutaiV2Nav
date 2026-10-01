package com.example.myapplication.data

import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.model.Musica
import com.example.myapplication.model.Playlist

object AppRepository {
    private var proximoMusicaId = 1
    private var proximoPlaylistId = 1

    val musicas = mutableStateListOf<Musica>()
    val playlists = mutableStateListOf<Playlist>()
    val historicoMusicas = mutableStateListOf<Musica>()

    init {
        // Dados iniciais
        val m1 = adicionarMusica("Starboy", "The Weeknd", "Starboy", 230, "R&B / Pop", isFavorito = true, reproducoes = 8420)
        val m2 = adicionarMusica("Party Monster", "The Weeknd", "Starboy", 249, "R&B", isFavorito = true, reproducoes = 5120)
        val m3 = adicionarMusica("Billie Jean", "Michael Jackson", "Thriller", 294, "Pop", isFavorito = false, reproducoes = 12400)
        val m4 = adicionarMusica("Rebel Yell", "Billy Idol", "Rebel Yell", 285, "Rock", isFavorito = true, reproducoes = 3210)
        val m5 = adicionarMusica("Blinding Lights", "The Weeknd", "After Hours", 200, "Synthpop", isFavorito = false, reproducoes = 9800)

        adicionarPlaylist("Playlist #861", "Minhas preferidas para treinar e correr", mutableListOf(1, 2, 3))
        adicionarPlaylist("Playlist #867", "Sessão Pop Nostalgia Anos 80 e 90", mutableListOf(3, 4))
        adicionarPlaylist("Noche de R&B", "Músicas para relaxar no fim de semana", mutableListOf(1, 2, 5))

        // Adiciona histórico inicial
        registrarNoHistorico(m1)
        registrarNoHistorico(m2)
        registrarNoHistorico(m5)
        registrarNoHistorico(m3)
    }

    fun adicionarMusica(
        titulo: String,
        artista: String,
        album: String,
        duracaoSegundos: Int,
        genero: String,
        isFavorito: Boolean = false,
        reproducoes: Int = 0
    ): Musica {
        val novaMusica = Musica(
            id = proximoMusicaId++,
            titulo = titulo,
            artista = artista,
            album = album,
            duracaoSegundos = duracaoSegundos,
            genero = genero,
            isFavorito = isFavorito,
            reproducoes = reproducoes
        )
        musicas.add(novaMusica)
        return novaMusica
    }

    fun removerMusica(musicaId: Int) {
        musicas.removeIf { it.id == musicaId }
        historicoMusicas.removeIf { it.id == musicaId }
        playlists.forEach { playlist ->
            playlist.musicasIds.remove(musicaId)
        }
    }

    fun toggleFavorito(musicaId: Int) {
        val index = musicas.indexOfFirst { it.id == musicaId }
        if (index != -1) {
            val m = musicas[index]
            musicas[index] = m.copy(isFavorito = !m.isFavorito)
        }
    }

    fun registrarNoHistorico(musica: Musica) {
        // Insere a música no topo do histórico sem duplicar seguidos
        if (historicoMusicas.firstOrNull()?.id != musica.id) {
            historicoMusicas.add(0, musica)
        }
    }

    fun buscarMusicaPorId(musicaId: Int): Musica? {
        return musicas.find { it.id == musicaId }
    }

    fun adicionarPlaylist(nome: String, descricao: String, musicasIds: MutableList<Int> = mutableListOf()): Playlist {
        val novaPlaylist = Playlist(
            id = proximoPlaylistId++,
            nome = nome,
            descricao = descricao,
            musicasIds = musicasIds
        )
        playlists.add(novaPlaylist)
        return novaPlaylist
    }

    fun removerPlaylist(playlistId: Int) {
        playlists.removeIf { it.id == playlistId }
    }

    fun buscarPlaylistPorId(playlistId: Int): Playlist? {
        return playlists.find { it.id == playlistId }
    }

    fun adicionarMusicaAPlaylist(playlistId: Int, musicaId: Int) {
        val playlist = buscarPlaylistPorId(playlistId)
        if (playlist != null && !playlist.musicasIds.contains(musicaId)) {
            val index = playlists.indexOfFirst { it.id == playlistId }
            if (index != -1) {
                val novaLista = playlist.musicasIds.toMutableList().apply { add(musicaId) }
                playlists[index] = playlist.copy(musicasIds = novaLista)
            }
        }
    }

    fun removerMusicaDaPlaylist(playlistId: Int, musicaId: Int) {
        val playlist = buscarPlaylistPorId(playlistId)
        if (playlist != null) {
            val index = playlists.indexOfFirst { it.id == playlistId }
            if (index != -1) {
                val novaLista = playlist.musicasIds.toMutableList().apply { remove(musicaId) }
                playlists[index] = playlist.copy(musicasIds = novaLista)
            }
        }
    }

    fun obterMusicasDaPlaylist(playlistId: Int): List<Musica> {
        val playlist = buscarPlaylistPorId(playlistId) ?: return emptyList()
        return musicas.filter { it.id in playlist.musicasIds }
    }

    fun calcularTempoTotalPlaylistSegundos(playlistId: Int): Int {
        val listaMusicas = obterMusicasDaPlaylist(playlistId)
        return listaMusicas.sumOf { it.duracaoSegundos }
    }

    fun obterMusicaMaisOuvidaDaPlaylist(playlistId: Int): Musica? {
        return obterMusicasDaPlaylist(playlistId).maxByOrNull { it.reproducoes }
    }
}
