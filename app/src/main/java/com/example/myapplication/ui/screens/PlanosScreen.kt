package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanosScreen(navController: NavController) {
    var planoSelecionado by remember { mutableStateOf("Anual") }
    var mensagemSucesso by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planos Mensais e Anuais", color = Color.White) },
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = "Premium",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Escolha o seu Plano VIP",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Ouça suas músicas sem interrupções e com a melhor qualidade de áudio.",
                fontSize = 14.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Card Plano Mensal ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (planoSelecionado == "Mensal") Color(0xFF8A1B88) else Color(0xFF1E1A3A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Plano Mensal", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Cobrança mensal recorrente", fontSize = 12.sp, color = Color.LightGray)
                        }
                        Text("R$ 19,90/mês", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE0AAFF))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("• Áudio Hi-Fi de alta fidelidade", color = Color.White, fontSize = 13.sp)
                    Text("• Sem anúncios comerciais", color = Color.White, fontSize = 13.sp)
                    Text("• Pule quantas faixas quiser", color = Color.White, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            planoSelecionado = "Mensal"
                            mensagemSucesso = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (planoSelecionado == "Mensal") Color.White else Color(0xFF8A1B88)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (planoSelecionado == "Mensal") "Plano Selecionado" else "Selecionar Plano Mensal",
                            color = if (planoSelecionado == "Mensal") Color(0xFF8A1B88) else Color.White
                        )
                    }
                }
            }

            // --- Card Plano Anual (Destaque) ---
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (planoSelecionado == "Anual") Color(0xFF8A1B88) else Color(0xFF1E1A3A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Plano Anual VIP", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFFF9800), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("ECONOMIZE 25%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                }
                            }
                            Text("Cobrado anualmente (R$ 14,99/mês)", fontSize = 12.sp, color = Color.LightGray)
                        }
                        Text("R$ 179,90/ano", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE0AAFF))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("• Todos os benefícios do Plano Mensal", color = Color.White, fontSize = 13.sp)
                    Text("• Downloads de playlists ilimitados offline", color = Color.White, fontSize = 13.sp)
                    Text("• Acesso prioritário a lançamentos e shows", color = Color.White, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            planoSelecionado = "Anual"
                            mensagemSucesso = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (planoSelecionado == "Anual") Color.White else Color(0xFF8A1B88)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (planoSelecionado == "Anual") "Plano Selecionado (Melhor Valor)" else "Selecionar Plano Anual",
                            color = if (planoSelecionado == "Anual") Color(0xFF8A1B88) else Color.White
                        )
                    }
                }
            }

            if (mensagemSucesso) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Parabéns! Você ativou o $planoSelecionado com sucesso!",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
