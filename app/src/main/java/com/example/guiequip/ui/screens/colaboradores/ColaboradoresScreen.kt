package com.example.guiequip.ui.screens.colaboradores

import ColaboradoresViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.guiequip.R
import com.example.guiequip.data.Colaborador
import com.example.guiequip.ui.screens.dashboard.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaboradoresScreen(
    navController: NavController,
    departamentoId: String,
    onAdd: () -> Unit,
    colaboradoresViewModel: ColaboradoresViewModel = viewModel()
) {
    // Fetch colaboradores quando o departamentoId mudar
    LaunchedEffect(departamentoId) {
        colaboradoresViewModel.fetchColaboradores(departamentoId)
    }

    val colaboradores by colaboradoresViewModel.colaboradores.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContentColaboradores(navController = navController, departamentoId = departamentoId)
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Logo do lado esquerdo
                            Image(
                                painter = painterResource(R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.height(90.dp),
                                alignment = Alignment.CenterStart
                            )
                            // Botão de menu hambúrguer do lado direito
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("criar_colaborador_screen/$departamentoId")
                }) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFF60A5FA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Adicionar Colaborador",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                }
            }

        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Colaboradores",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                if (colaboradores.isEmpty()) {
                    // Exibe mensagem caso não haja colaboradores
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum colaborador encontrado.",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 64.dp)
                    ) {
                        items(colaboradores) { colaborador ->
                            ColaboradorCard(
                                colaborador = colaborador,
                                onEdit = { navController.navigate("atualizar_colaborador_screen/${colaborador.id}") },
                                onDelete = { colaboradoresViewModel.deleteColaborador(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ColaboradorCard(
    colaborador: Colaborador,
    onEdit: (Colaborador) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Opcional: ação ao clicar no card inteiro */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nome do colaborador
            Text(
                text = colaborador.nome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = colaborador.cargo,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                IconButton(onClick = { onEdit(colaborador) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Editar Colaborador",
                        modifier = Modifier.size(24.dp),

                    )
                }

                IconButton(onClick = { onDelete(colaborador.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Excluir Colaborador",
                        modifier = Modifier.size(24.dp),

                    )
                }
            }
        }
    }
}


@Composable
fun DrawerContentColaboradores(navController: NavController, departamentoId: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxSize(0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), // Garante que ocupe a largura total dentro da Box
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Botão Voltar
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
            // Botão Dashboard
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
            // Botão Equipamentos
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
            // Botão Colaboradores
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
            // Botão Sair
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
