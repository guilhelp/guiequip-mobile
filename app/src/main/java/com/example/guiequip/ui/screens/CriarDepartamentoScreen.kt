package com.example.guiequip.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guiequip.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarDepartamentoScreen(onBack: () -> Unit, onSubmit: (String, String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Criar Departamento")
                },
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
                .padding(top = 16.dp)
        ) {
                // Formulário
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Título
                        Text(
                            text = "Cadastrar Departamento",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Divider(
                            modifier = Modifier
                                .width(64.dp)
                                .height(2.dp)
                                .padding(top = 8.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color(0xFF60A5FA) // Azul
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Nome
                        Text(text = "Nome do Departamento", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        OutlinedTextField(
                            value = "",
                            onValueChange = { /* Atualizar estado */ },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo Descrição
                        Text(text = "Descrição do Departamento", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        OutlinedTextField(
                            value = "",
                            onValueChange = { /* Atualizar estado */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            singleLine = false,
                            placeholder = { Text("Digite a descrição do departamento") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Botão
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { onSubmit("Novo Departamento", "Descrição do departamento") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF60A5FA), // Azul
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text("Cadastrar")
                            }
                        }
                    }
                }
            }
        }
    }

