import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guiequip.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.guiequip.data.Departamento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartamentosScreen(
    navController: NavHostController,
    departamentos: List<Departamento>,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit,
    onAdd: () -> Unit,
    departamentosViewModel: DepartamentosViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        departamentosViewModel.fetchDepartamentos()
    }

    val departamentos by departamentosViewModel.departamentos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.height(90.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
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
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(departamentos) { departamento ->
                    DepartamentoCard(
                        departamento = departamento,
                        onEdit = onEdit,
                        onDelete = { departamentoId ->
                            departamentosViewModel.deleteDepartamento(departamentoId)
                        },
                        navController = navController // Passando o navController
                    )
                }
            }
        }
    }
}

@Composable
fun DepartamentoCard(
    departamento: Departamento,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit,
    navController: NavHostController // Parâmetro navController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                if (!departamento.id.isNullOrEmpty()) {
                    navController.navigate("dashboard_screen/${departamento.id}")
                } else {
                    Log.e("DepartamentoCard", "Erro: departamentoId está vazio!")
                }
            },
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
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Editar",
                        modifier = Modifier.size(30.dp),
                    )
                }
                IconButton(onClick = { onDelete(departamento.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Deletar",
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
        }
    }
}




