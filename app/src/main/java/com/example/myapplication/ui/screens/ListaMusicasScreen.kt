package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.AppRepository
import com.example.myapplication.navigation.Rotas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMusicasScreen(navController: NavController) {
    // Formulário para ADICIONAR nova música (Requisito 3.2)
    var titulo by remember { mutableStateOf("") }
    var artista by remember { mutableStateOf("") }
    var duracaoTexto by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var erroFormulario by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca de Músicas", color = Color.White) },
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
            // --- Formulário de Cadastro de Nova Música ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1A3A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Adicionar Nova Música",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE0AAFF)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título da Música") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFD946EF),
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = artista,
                            onValueChange = { artista = it },
                            label = { Text("Artista") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFD946EF),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        OutlinedTextField(
                            value = duracaoTexto,
                            onValueChange = { duracaoTexto = it },
                            label = { Text("Duração (s)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFD946EF),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = genero,
                        onValueChange = { genero = it },
                        label = { Text("Gênero Musical") },
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
                            text = "Preencha todos os campos corretamente!",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val duracaoSeg = duracaoTexto.toIntOrNull()
                            if (titulo.isNotBlank() && artista.isNotBlank() && duracaoSeg != null) {
                                AppRepository.adicionarMusica(
                                    titulo = titulo,
                                    artista = artista,
                                    album = "Single",
                                    duracaoSegundos = duracaoSeg,
                                    genero = if (genero.isBlank()) "Pop" else genero
                                )
                                // Limpa o formulário
                                titulo = ""
                                artista = ""
                                duracaoTexto = ""
                                genero = ""
                                erroFormulario = false
                            } else {
                                erroFormulario = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A1B88)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Adicionar Música")
                    }
                }
            }

            Text(
                text = "Músicas Cadastradas (${AppRepository.musicas.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // --- Lista de Músicas com LazyColumn + Card (Requisito 3.2) ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(AppRepository.musicas, key = { it.id }) { musica ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1A3A)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Navega para a tela de detalhes da música
                                navController.navigate(Rotas.detalheMusica(musica.id))
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Música",
                                    tint = Color(0xFFD946EF),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = musica.titulo,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${musica.artista} • ${musica.duracaoFormatada()} • ${musica.genero}",
                                        fontSize = 13.sp,
                                        color = Color.LightGray
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Botão de Favorito (Toggle)
                                IconButton(onClick = { AppRepository.toggleFavorito(musica.id) }) {
                                    Icon(
                                        imageVector = if (musica.isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorito",
                                        tint = if (musica.isFavorito) Color.Red else Color.Gray
                                    )
                                }

                                // Botão de REMOVER (Requisito 3.2)
                                IconButton(onClick = { AppRepository.removerMusica(musica.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remover Música",
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
