package com.example.guiequip.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guiequip.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartamentosScreen(departamentos: List<Departamento>,
                        onEdit: (String) -> Unit,
                        onDelete: (String) -> Unit,
                        onAdd: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier

                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo), // Substitua "logo" pelo nome correto do recurso
                            contentDescription = "Logo",
                            modifier = Modifier.height(90.dp), // Aumentado de 56.dp para 90.dp
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { // Navegar para a tela de criação
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF60A5FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Adicionar Departamento",
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
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Bem vindo(a), faça a escolha do departamento:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 64.dp) // Para acomodar o FAB
            ) {
                items(departamentos) { departamento ->
                    DepartamentoCard(
                        departamento = departamento,
                        onEdit = onEdit,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Composable
fun DepartamentoCard(departamento: Departamento, onEdit: (String) -> Unit, onDelete: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navegar para detalhes do departamento */ },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = departamento.nome, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = departamento.descricao, fontSize = 16.sp, color = Color.Gray)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                IconButton(onClick = { onEdit(departamento.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit), // Substitua "ic_edit" pelo recurso correto
                        contentDescription = "Editar",
                        modifier = Modifier.size(30.dp), // Diminuído o tamanho do ícone

                    )
                }
                IconButton(onClick = { onDelete(departamento.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete), // Substitua "ic_delete" pelo recurso correto
                        contentDescription = "Deletar",
                        modifier = Modifier.size(30.dp), // Diminuído o tamanho do ícone

                    )
                }
            }
        }
    }
}


data class Departamento(val id: String, val nome: String, val descricao: String)

