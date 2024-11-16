package com.example.guiequip.ui.screens.departamentos

import DepartamentosViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.guiequip.R
import com.google.firebase.firestore.FirebaseFirestore

fun atualizarDepartamento(departamentoId: String, nome: String, descricao: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val departamento = hashMapOf(
        "nome" to nome,
        "descricao" to descricao
    )

    db.collection("departamentos")
        .document(departamentoId)
        .set(departamento)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizarDepartamentoScreen(
    departamentoId: String,
    nomeInicial: String,
    descricaoInicial: String,
    navController: NavHostController
) {
    var nome by remember { mutableStateOf(nomeInicial) }
    var descricao by remember { mutableStateOf(descricaoInicial) }

    val viewModel: DepartamentosViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atualizar Departamento") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(R.drawable.ic_back), contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                // Campo Nome
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Departamento") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Campo Descrição
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição do Departamento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botão de Atualizar
                Button(
                    onClick = {
                        if (nome.isNotBlank() && descricao.isNotBlank()) {
                            viewModel.atualizarDepartamento(departamentoId, nome, descricao,
                                onSuccess = {
                                    Log.d("AtualizarDepartamento", "Departamento atualizado com sucesso")
                                    navController.popBackStack() // Voltar para a tela anterior
                                },
                                onError = { exception ->
                                    Log.e("AtualizarDepartamento", "Erro ao atualizar departamento: ${exception.message}")
                                }
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF60A5FA),
                        contentColor = Color.White
                    ),
                ) {
                    Text("Atualizar")
                }
            }
        }
    )
}




