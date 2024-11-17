package com.example.guiequip.ui.screens.dashboard

import ColaboradoresViewModel
import EquipamentosViewModel
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.guiequip.R
import com.example.guiequip.ui.screens.equipamentos.DrawerContentEquipamentos
import com.example.guiequip.ui.theme.JetBrainsMono
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    departamentoId: String,
    equipamentosViewModel: EquipamentosViewModel = viewModel(),
    colaboradoresViewModel: ColaboradoresViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var totalEquipamentos by remember { mutableStateOf(0) }
    var totalColaboradores by remember { mutableStateOf(0) }

    LaunchedEffect(departamentoId) {
        equipamentosViewModel.fetchEquipamentos(departamentoId)
        colaboradoresViewModel.fetchColaboradores(departamentoId)
    }

    val equipamentos = equipamentosViewModel.equipamentos.collectAsState(initial = emptyList())
    val colaboradores = colaboradoresViewModel.colaboradores.collectAsState(initial = emptyList())

    totalEquipamentos = equipamentos.value.size
    totalColaboradores = colaboradores.value.size

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (drawerState.isOpen) {
                DrawerContent(navController = navController, departamentoId = departamentoId)
            }
        },
        gesturesEnabled = drawerState.isOpen
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
                            Image(
                                painter = painterResource(R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.height(90.dp)
                            )
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),

                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = totalEquipamentos.toString(),
                                style = TextStyle(fontFamily = JetBrainsMono, fontSize = 72.sp, fontWeight = FontWeight.Bold),


                            )
                            Text(
                                text = "Total de Equipamentos",
                                style = TextStyle(fontFamily = JetBrainsMono, fontSize = 16.sp),

                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = totalColaboradores.toString(),
                                style = TextStyle(fontFamily = JetBrainsMono, fontSize = 72.sp, fontWeight = FontWeight.Bold),

                            )
                            Text(
                                text = "Total de Colaboradores",
                                style = TextStyle(fontFamily = JetBrainsMono, fontSize = 16.sp),

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
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TextButton(onClick = { navController.navigate("departamentos_screen") }) {
                    Text(
                        text = "Voltar",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )
                }
                TextButton(onClick = { navController.navigate("dashboard_screen/$departamentoId") }) {
                    Text(
                        text = "Dashboard",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )
                }
                TextButton(onClick = { navController.navigate("equipamentos_screen/$departamentoId") }) {
                    Text(
                        text = "Equipamentos",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )
                }
                TextButton(onClick = { navController.navigate("colaboradores_screen/$departamentoId") }) {
                    Text(
                        text = "Colaboradores",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )
                }
            }
            TextButton(onClick = { navController.navigate("login_screen") }) {
                Text(
                    text = "Sair",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black
                )
            }
        }
    }
}



