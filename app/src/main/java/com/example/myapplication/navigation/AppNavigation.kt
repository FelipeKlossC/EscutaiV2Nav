package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*

sealed class BottomNavItem(val rota: String, val titulo: String, val icone: ImageVector) {
    object Home : BottomNavItem(Rotas.HOME, "Início", Icons.Default.Home)
    object Musicas : BottomNavItem(Rotas.LISTA_MUSICAS, "Músicas", Icons.Default.MusicNote)
    object Playlists : BottomNavItem(Rotas.LISTA_PLAYLISTS, "Playlists", Icons.Default.QueueMusic)
    object Favoritos : BottomNavItem(Rotas.FAVORITOS, "Favoritos", Icons.Default.Favorite)
    object TopStats : BottomNavItem(Rotas.TOP_STATS, "Top", Icons.Default.Whatshot)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Musicas,
        BottomNavItem.Playlists,
        BottomNavItem.Favoritos,
        BottomNavItem.TopStats
    )

    Scaffold(
        bottomBar = {
            // Requisito 3.1: BottomNavigation / NavigationBar funcionando de verdade
            NavigationBar(
                containerColor = Color(0xFF1E1A3A),
                contentColor = Color.White
            ) {
                bottomItems.forEach { item ->
                    val isSelected = currentRoute == item.rota
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != item.rota) {
                                navController.navigate(item.rota) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(item.icone, contentDescription = item.titulo) },
                        label = { Text(item.titulo) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color(0xFFE0AAFF),
                            indicatorColor = Color(0xFF8A1B88),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        // Central NavHost (Requisito 3.1)
        NavHost(
            navController = navController,
            startDestination = Rotas.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Tela Home
            composable(Rotas.HOME) {
                HomeScreen(navController = navController)
            }

            // 2. Tela de Lista de Músicas
            composable(Rotas.LISTA_MUSICAS) {
                ListaMusicasScreen(navController = navController)
            }

            // 3. Tela de Detalhe da Música (com argumento de rota)
            composable(
                route = "${Rotas.DETALHE_MUSICA}/{musicaId}",
                arguments = listOf(navArgument("musicaId") { type = NavType.IntType })
            ) { backStackEntry ->
                val musicaId = backStackEntry.arguments?.getInt("musicaId") ?: 0
                DetalheMusicaScreen(navController = navController, musicaId = musicaId)
            }

            // 4. Tela de Lista de Playlists
            composable(Rotas.LISTA_PLAYLISTS) {
                ListaPlaylistsScreen(navController = navController)
            }

            // 5. Tela de Detalhe da Playlist (com argumento de rota)
            composable(
                route = "${Rotas.DETALHE_PLAYLIST}/{playlistId}",
                arguments = listOf(navArgument("playlistId") { type = NavType.IntType })
            ) { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: 0
                DetalhePlaylistScreen(navController = navController, playlistId = playlistId)
            }

            // 6. Tela de Favoritos
            composable(Rotas.FAVORITOS) {
                FavoritosScreen(navController = navController)
            }

            // 7. Tela de Top / Ranking
            composable(Rotas.TOP_STATS) {
                TopStatsScreen(navController = navController)
            }

            // 8. Tela de Perfil
            composable(Rotas.PERFIL) {
                PerfilScreen(navController = navController)
            }

            // 9. Tela de Planos (Mensais e Anuais)
            composable(Rotas.PLANOS) {
                PlanosScreen(navController = navController)
            }

            // 10. Tela de Histórico de Execuções
            composable(Rotas.HISTORICO) {
                HistoricoScreen(navController = navController)
            }
        }
    }
}
