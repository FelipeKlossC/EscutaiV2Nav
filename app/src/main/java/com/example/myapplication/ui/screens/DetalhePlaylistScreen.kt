package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.AppRepository
import com.example.myapplication.navigation.Rotas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhePlaylistScreen(navController: NavController, playlistId: Int) {
    val playlist = AppRepository.buscarPlaylistPorId(playlistId)
    var showDialogAdicionarMusica by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playlist?.nome ?: "Detalhes da Playlist", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E1A3A)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                }
            )
        },
        containerColor = Color(0xFF0D0B1D)
    ) { paddingValues ->
        if (playlist == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Playlist não encontrada!", color = Color.White, fontSize = 18.sp)
            }
        } else {
            val musicasDaPlaylist = AppRepository.obterMusicasDaPlaylist(playlistId)
            val tempoTotalSegundos = AppRepository.calcularTempoTotalPlaylistSegundos(playlistId)
            val tempoTotalMinutos = tempoTotalSegundos / 60
            val musicaMaisOuvida = AppRepository.obterMusicaMaisOuvidaDaPlaylist(playlistId)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Card de Resumo e Métricas Calculadas (Passo a mais de Dificuldade - Seção 3.2)
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF381140)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = playlist.nome,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = playlist.descricao,
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.DarkGray)

                        // MÉTROLOGIA CALCULADA / DADOS COMBINADOS DAS 2 DATA CLASSES
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Total de Faixas", fontSize = 12.sp, color = Color.Gray)
                                Text("${musicasDaPlaylist.size}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE0AAFF))
                            }
                            Column {
                                Text("Duração Total", fontSize = 12.sp, color = Color.Gray)
                                Text("$tempoTotalMinutos min", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE0AAFF))
                            }
                            Column {
                                Text("Destaque", fontSize = 12.sp, color = Color.Gray)
                                Text(musicaMaisOuvida?.titulo ?: "-", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD946EF))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Músicas nesta Playlist",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = { showDialogAdicionarMusica = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Incluir Músicas", tint = Color(0xFFD946EF))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Lista combinada das Músicas pertencentes a essa Playlist
                if (musicasDaPlaylist.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Esta playlist ainda não tem músicas. Clique em + para incluir!", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(musicasDaPlaylist, key = { it.id }) { musica ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1A3A)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate(Rotas.detalheMusica(musica.id)) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = musica.titulo,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "${musica.artista} • ${musica.duracaoFormatada()}",
                                            fontSize = 13.sp,
                                            color = Color.LightGray
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            AppRepository.removerMusicaDaPlaylist(playlistId, musica.id)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remover da Playlist",
                                            tint = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal/Dialog para adicionar música da biblioteca global à playlist
    if (showDialogAdicionarMusica && playlist != null) {
        val musicasForaDaPlaylist = AppRepository.musicas.filter { it.id !in playlist.musicasIds }

        AlertDialog(
            onDismissRequest = { showDialogAdicionarMusica = false },
            title = { Text("Adicionar Música à Playlist", color = Color.White) },
            containerColor = Color(0xFF1E1A3A),
            text = {
                if (musicasForaDaPlaylist.isEmpty()) {
                    Text("Todas as músicas da biblioteca já estão nesta playlist!", color = Color.LightGray)
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(musicasForaDaPlaylist) { m ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        AppRepository.adicionarMusicaAPlaylist(playlistId, m.id)
                                        showDialogAdicionarMusica = false
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(m.titulo, fontWeight = FontWeight.Bold, color = Color.White)
                                    Text(m.artista, fontSize = 12.sp, color = Color.Gray)
                                }
                                Icon(Icons.Default.AddCircle, contentDescription = "Incluir", tint = Color(0xFFD946EF))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialogAdicionarMusica = false }) {
                    Text("Fechar", color = Color(0xFFE0AAFF))
                }
            }
        )
    }
}
