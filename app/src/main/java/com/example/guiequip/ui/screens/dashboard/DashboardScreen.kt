package com.example.guiequip.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.guiequip.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, totalEquipamentos: Int, totalColaboradores: Int, departamentoId: String) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController, departamentoId = departamentoId)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Logo
                            Image(
                                painter = painterResource(R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.height(90.dp)
                            )
                            // Menu Hambúrguer
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = totalEquipamentos.toString(),
                                style = TextStyle(fontSize = 72.sp, fontWeight = FontWeight.Bold),
                                color = Color.Black
                            )
                            Text(
                                text = "Total de Equipamentos",
                                style = TextStyle(fontSize = 16.sp),
                                color = Color.Black
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = totalColaboradores.toString(),
                                style = TextStyle(fontSize = 72.sp, fontWeight = FontWeight.Bold),
                                color = Color.Black
                            )
                            Text(
                                text = "Total de Colaboradores",
                                style = TextStyle(fontSize = 16.sp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(navController: NavController, departamentoId: String) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),

    ) {
        // Botão Voltar
        TextButton(onClick = {
            navController.popBackStack()
        }) {
            Text(
                text = "Voltar",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }
        // Botão Equipamentos
        TextButton(onClick = {
            navController.navigate("departamentos_screen")
        }) {
            Text(
                text = "Equipamentos",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }
        // Botão Colaboradores
        TextButton(onClick = {
            navController.navigate("colaboradores_screen/$departamentoId")
        }) {
            Text(
                text = "Colaboradores",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }
        // Botão Sair
        TextButton(onClick = {
            navController.navigate("login_screen")
        }) {
            Text(
                text = "Sair",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }
    }
}


