package com.example.guiequip.ui.screens.equipamentos

import ColaboradoresViewModel
import EquipamentosViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.guiequip.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guiequip.data.Equipamento
import com.example.guiequip.data.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarEquipamentoScreen(
    departamentoId: String,
    onBack: () -> Unit,
    equipamentosViewModel: EquipamentosViewModel = viewModel(),
    colaboradoresViewModel: ColaboradoresViewModel = viewModel(),
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    var tipoEquipamento by remember { mutableStateOf("") }
    var processador by remember { mutableStateOf("") }
    var ram by remember { mutableStateOf("") }
    var hdSsd by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var colaboradorPesquisa by remember { mutableStateOf(TextFieldValue("")) }
    var colaboradorSelecionado by remember { mutableStateOf<Pair<String, String>?>(null) }
    var tipoEquipamentoMenuExpanded by remember { mutableStateOf(false) }
    var colaboradoresMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(departamentoId) {
        colaboradoresViewModel.fetchColaboradores(departamentoId)
    }

    val colaboradores = colaboradoresViewModel.colaboradores.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Equipamento") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box {
                OutlinedTextField(
                    value = tipoEquipamento,
                    onValueChange = { },
                    label = { Text("Tipo de Equipamento") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { tipoEquipamentoMenuExpanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir")
                        }
                    }
                )
                DropdownMenu(
                    expanded = tipoEquipamentoMenuExpanded,
                    onDismissRequest = { tipoEquipamentoMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Notebook") },
                        onClick = {
                            tipoEquipamento = "Notebook"
                            tipoEquipamentoMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Monitor") },
                        onClick = {
                            tipoEquipamento = "Monitor"
                            tipoEquipamentoMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Teclado/Mouse") },
                        onClick = {
                            tipoEquipamento = "Teclado/Mouse"
                            tipoEquipamentoMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Headset") },
                        onClick = {
                            tipoEquipamento = "Headset"
                            tipoEquipamentoMenuExpanded = false
                        }
                    )
                }
            }

            if (tipoEquipamento == "Notebook") {
                OutlinedTextField(
                    value = processador,
                    onValueChange = { processador = it },
                    label = { Text("Processador") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = ram,
                    onValueChange = { ram = it },
                    label = { Text("RAM") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = hdSsd,
                    onValueChange = { hdSsd = it },
                    label = { Text("HD/SSD") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
            }

            if (tipoEquipamento.isNotEmpty()) {
                OutlinedTextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = { Text("Modelo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
            }

            OutlinedTextField(
                value = colaboradorPesquisa,
                onValueChange = { colaboradorPesquisa = it },
                label = { Text("Pesquisar Colaborador") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF60A5FA),
                    unfocusedBorderColor = Color(0xFF60A5FA),
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                ),
                trailingIcon = {
                    IconButton(onClick = { colaboradoresMenuExpanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir")
                    }
                }
            )
            DropdownMenu(
                expanded = colaboradoresMenuExpanded,
                onDismissRequest = { colaboradoresMenuExpanded = false }
            ) {
                colaboradores
                    .filter { it.nome.contains(colaboradorPesquisa.text, ignoreCase = true) }
                    .forEach { colaborador ->
                        DropdownMenuItem(
                            text = { Text(colaborador.nome) },
                            onClick = {
                                colaboradorSelecionado = colaborador.id to colaborador.nome
                                colaboradorPesquisa = TextFieldValue(colaborador.nome)
                                colaboradoresMenuExpanded = false
                            }
                        )
                    }
            }

            Button(
                onClick = {
                    val equipamentoData = mutableMapOf(
                        "departamentoId" to departamentoId,
                        "tipoEquipamento" to tipoEquipamento,
                        "marca" to marca,
                        "modelo" to modelo,
                        "usuario" to mapOf(
                            "id" to (colaboradorSelecionado?.first ?: ""),
                            "nome" to (colaboradorSelecionado?.second ?: "")
                        )
                    )

                    if (tipoEquipamento == "Notebook") {
                        equipamentoData["processador"] = processador
                        equipamentoData["ram"] = ram
                        equipamentoData["hdSsd"] = hdSsd
                    }

                    equipamentosViewModel.createEquipamento(
                        equipamentoData = equipamentoData,
                        onSuccess = { onSuccess() },
                        onError = { onError(it) }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF60A5FA),
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Cadastrar")
            }

        }
    }
}


