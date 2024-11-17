package com.example.guiequip.ui.screens.colaboradores

import ColaboradoresViewModel
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.guiequip.R
import com.example.guiequip.data.Colaborador
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

fun atualizarColaborador(
    colaboradorId: String,
    nome: String,
    cargo: String,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val colaborador = hashMapOf(
        "nome" to nome,
        "cargo" to cargo
    )

    db.collection("colaboradores")
        .document(colaboradorId)
        .set(colaborador, SetOptions.merge())
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizarColaboradorScreen(
    navController: NavHostController,
    colaboradorId: String
) {
    var nome by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(colaboradorId) {
        db.collection("colaboradores")
            .document(colaboradorId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    nome = document.getString("nome") ?: ""
                    cargo = document.getString("cargo") ?: ""
                }
            }
            .addOnFailureListener { exception ->
                Log.e("AtualizarColaborador", "Erro ao buscar colaborador: ${exception.message}")
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atualizar Colaborador") },
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

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Colaborador") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = cargo,
                    onValueChange = { cargo = it },
                    label = { Text("Cargo do Colaborador") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF60A5FA),
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        if (nome.isNotBlank() && cargo.isNotBlank()) {
                            atualizarColaborador(colaboradorId, nome, cargo,
                                onSuccess = {
                                    Log.d("AtualizarColaborador", "Colaborador atualizado com sucesso")
                                    navController.popBackStack() // Voltar para a tela anterior
                                },
                                onError = { exception ->
                                    Log.e("AtualizarColaborador", "Erro ao atualizar colaborador: ${exception.message}")
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

