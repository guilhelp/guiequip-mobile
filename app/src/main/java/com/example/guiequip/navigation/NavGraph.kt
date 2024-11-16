package com.example.guiequip.navigation

import DepartamentosScreen
import DepartamentosViewModel
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.guiequip.ui.screens.departamentos.CriarDepartamentoScreen
import com.example.guiequip.ui.screens.LoginScreen
import com.example.guiequip.ui.screens.colaboradores.AtualizarColaboradorScreen
import com.example.guiequip.ui.screens.colaboradores.ColaboradoresScreen
import com.example.guiequip.ui.screens.colaboradores.CriarColaboradorScreen
import com.example.guiequip.ui.screens.colaboradores.criarColaborador
import com.example.guiequip.ui.screens.dashboard.DashboardScreen
import com.example.guiequip.ui.screens.departamentos.AtualizarDepartamentoScreen
import com.example.guiequip.ui.screens.departamentos.criarDepartamento

@Composable
fun NavGraph(navController: NavHostController) {
    // Inicializando o ViewModel
    val departamentosViewModel: DepartamentosViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }

        composable("departamentos_screen") {
            DepartamentosScreen(
                navController = navController,
                departamentos = departamentosViewModel.departamentos.collectAsState().value, // Usando o estado do ViewModel
                onEdit = { departamentoId ->

                    departamentosViewModel.buscarDepartamentoPorId(
                        departamentoId,
                        onSuccess = { departamento ->
                            navController.navigate("atualizar_departamento_screen/${departamento.id}/${departamento.nome}/${departamento.descricao}")
                        },
                        onError = { exception ->

                            println("Erro ao buscar departamento: ${exception.message}")
                        }
                    )
                },
                onDelete = { departamentoId ->
                    // Lógica de exclusão
                },
                onAdd = {
                    navController.navigate("criar_departamento_screen")
                }
            )
        }

        composable("dashboard_screen/{departamentoId}") { backStackEntry ->
            val departamentoId = backStackEntry.arguments?.getString("departamentoId") ?: ""
            DashboardScreen(
                navController = navController,
                totalEquipamentos = 10,
                totalColaboradores = 5,
                departamentoId = departamentoId
            )
        }

        composable("atualizar_colaborador_screen/{colaboradorId}") { backStackEntry ->
            val colaboradorId = backStackEntry.arguments?.getString("colaboradorId") ?: ""
            AtualizarColaboradorScreen(
                navController = navController,
                colaboradorId = colaboradorId
            )
        }

        composable("criar_colaborador_screen/{departamentoId}") { backStackEntry ->
            val departamentoId = backStackEntry.arguments?.getString("departamentoId") ?: ""
            CriarColaboradorScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { nome, cargo ->
                    criarColaborador(
                        nome = nome,
                        cargo = cargo,
                        departamentoId = departamentoId,
                        onSuccess = {
                            Log.d("CriarColaborador", "Colaborador criado com sucesso")
                            navController.popBackStack() // Voltar para a tela anterior após criar
                        },
                        onError = { exception ->
                            Log.e("CriarColaborador", "Erro ao criar colaborador: ${exception.message}")
                        }
                    )
                }
            )
        }




        composable("colaboradores_screen/{departamentoId}") { backStackEntry ->
            val departamentoId = backStackEntry.arguments?.getString("departamentoId") ?: ""
            ColaboradoresScreen(
                navController = navController,
                departamentoId = departamentoId,
                onAdd = { navController.navigate("cadastrar_colaborador_screen/$departamentoId") }
            )
        }

        composable("criar_departamento_screen") {
            CriarDepartamentoScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { nome, descricao ->
                    criarDepartamento(
                        nome = nome,
                        descricao = descricao,
                        onSuccess = {
                            println("Departamento cadastrado com sucesso: $nome, $descricao")
                            navController.popBackStack()
                        },
                        onError = { exception ->
                            println("Erro ao cadastrar departamento: ${exception.message}")
                        }
                    )
                }
            )
        }

        composable(
            "atualizar_departamento_screen/{departamentoId}/{nome}/{descricao}"
        ) { backStackEntry ->
            val departamentoId = backStackEntry.arguments?.getString("departamentoId") ?: ""
            val nome = backStackEntry.arguments?.getString("nome") ?: ""
            val descricao = backStackEntry.arguments?.getString("descricao") ?: ""

            AtualizarDepartamentoScreen(
                departamentoId = departamentoId,
                nomeInicial = nome,
                descricaoInicial = descricao,
                navController = navController
            )
        }
    }
}

