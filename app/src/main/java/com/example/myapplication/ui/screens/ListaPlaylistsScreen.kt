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
fun ListaPlaylistsScreen(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var erroFormulario by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suas Playlists", color = Color.White) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- Formulário de Cadastro de Playlist ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1A3A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Criar Nova Playlist",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE0AAFF)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome da Playlist") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFD946EF),
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFD946EF),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    if (erroFormulario) {
                        Text(
                            text = "Insira um nome válido para a playlist!",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (nome.isNotBlank()) {
                                AppRepository.adicionarPlaylist(
                                    nome = nome,
                                    descricao = if (descricao.isBlank()) "Playlist personalizada" else descricao
                                )
                                nome = ""
                                descricao = ""
                                erroFormulario = false
                            } else {
                                erroFormulario = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A1B88)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Queue, contentDescription = "Criar")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Criar Playlist")
                    }
                }
            }

            Text(
                text = "Playlists Disponíveis (${AppRepository.playlists.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // --- Lista com LazyColumn + Card (Requisito 3.2) ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(AppRepository.playlists, key = { it.id }) { playlist ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF8A1B88)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Rotas.detalhePlaylist(playlist.id))
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = playlist.nome,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = playlist.descricao,
                                    fontSize = 13.sp,
                                    color = Color.LightGray
                                )
                                Text(
                                    text = "${playlist.musicasIds.size} músicas na lista",
                                    fontSize = 12.sp,
                                    color = Color(0xFFE0AAFF),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { AppRepository.removerPlaylist(playlist.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Excluir Playlist",
                                        tint = Color(0xFFFF5555)
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
