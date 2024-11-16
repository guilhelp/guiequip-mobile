package com.example.guiequip.ui.screens.equipamentos

import EquipamentosViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.guiequip.R
import com.example.guiequip.data.Equipamento
import com.example.guiequip.ui.screens.dashboard.DrawerContent
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipamentosScreen(
    navController: NavController,
    departamentoId: String,
    equipamentosViewModel: EquipamentosViewModel = viewModel(), // Inicializa ou injeta o ViewModel
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    LaunchedEffect(departamentoId) {
        equipamentosViewModel.fetchEquipamentos(departamentoId)
    }

    val equipamentos = equipamentosViewModel.equipamentos.collectAsState(initial = emptyList())

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContentEquipamentos(navController = navController, departamentoId = departamentoId)
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
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
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
                            contentDescription = "Adicionar Equipamento",
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
                    text = "Equipamentos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                if (equipamentos.value.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum equipamento encontrado.")
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 64.dp)
                    ) {
                        items(equipamentos.value) { equipamento ->
                            EquipamentoCard(
                                equipamento = equipamento,
                                onEdit = { onEdit(equipamento.id ?: "") },
                                onDelete = { onDelete(equipamento.id ?: "") }
                            )
                        }
                    }
                }
            }
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerContentEquipamentos(navController: NavController, departamentoId: String) {
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




@Composable
fun EquipamentoCard(
    equipamento: Equipamento,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Ação opcional ao clicar no card */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Exibe os detalhes básicos do equipamento
            Text(
                text = "${equipamento.tipoEquipamento} | ${equipamento.marca} | ${equipamento.modelo}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Exibe os detalhes adicionais somente se for um notebook
            if (equipamento.tipoEquipamento == "Notebook") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${equipamento.processador} | ${equipamento.ram} | ${equipamento.hdSsd}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = equipamento.usuario.nome.takeIf { it.isNotEmpty() }
                    ?.let { "Usuário: $it" }
                    ?: "Usuário: Sem usuário atribuído",
                fontSize = 16.sp,
                color = if (equipamento.usuario.nome.isEmpty()) Color.Red else Color.Black,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEdit(equipamento.id ?: "") }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Editar Equipamento",
                        modifier = Modifier.size(30.dp),
                    )
                }

                IconButton(onClick = { onDelete(equipamento.id ?: "") }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Deletar Equipamento",
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
        }
    }
}

