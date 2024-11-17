package com.example.guiequip.ui.screens.departamentos

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
import com.example.guiequip.R
import com.google.firebase.firestore.FirebaseFirestore

fun criarDepartamento(nome: String, descricao: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val departamento = hashMapOf(
        "nome" to nome,
        "descricao" to descricao
    )

    db.collection("departamentos")
        .add(departamento)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarDepartamentoScreen(onBack: () -> Unit, onSubmit: (String, String) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Departamento") },
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
                .padding(horizontal = 16.dp)
        ) {
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

            Button(
                onClick = {
                    if (nome.isNotBlank() && descricao.isNotBlank()) {
                        onSubmit(nome, descricao)
                    }
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



