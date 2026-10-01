package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.AppRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheMusicaScreen(navController: NavController, musicaId: Int) {
    val musica = AppRepository.buscarMusicaPorId(musicaId)
    var isPlaying by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Faixa", color = Color.White) },
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
        if (musica == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Música não encontrada!", color = Color.White, fontSize = 18.sp)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ilustração de capa da música
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF8A1B88), Color(0xFF1E1A3A))
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Capa da Música",
                        tint = Color.White,
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Nome da música e Artista
                Text(
                    text = musica.titulo,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = musica.artista,
                    fontSize = 18.sp,
                    color = Color(0xFFE0AAFF)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Card com Dados Detalhados
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1A3A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Álbum:", color = Color.Gray)
                            Text(musica.album, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Gênero:", color = Color.Gray)
                            Text(musica.genero, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Duração:", color = Color.Gray)
                            Text(musica.duracaoFormatada(), color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.DarkGray)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total de Execuções:", color = Color.Gray)
                            Text("${musica.reproducoes} vezes", color = Color(0xFFD946EF), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botões de Interação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { AppRepository.toggleFavorito(musica.id) }) {
                        Icon(
                            imageVector = if (musica.isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = if (musica.isFavorito) Color.Red else Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            isPlaying = !isPlaying
                            if (isPlaying) {
                                musica.reproducoes++
                            }
                        },
                        shape = CircleShape,
                        containerColor = Color(0xFF8A1B88),
                        contentColor = Color.White,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Tocar Faixa",
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    IconButton(onClick = { /* Compartilhar faixa */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartilhar",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
