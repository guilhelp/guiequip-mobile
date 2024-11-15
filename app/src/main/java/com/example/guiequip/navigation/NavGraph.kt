package com.example.guiequip.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.guiequip.ui.screens.CriarDepartamentoScreen
import com.example.guiequip.ui.screens.Departamento
import com.example.guiequip.ui.screens.LoginScreen
import com.example.guiequip.ui.screens.DepartamentosScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController = navController) // Passa a função para a tela de login
        }

        composable("departamentos_screen") {
            val departamentos = remember {
                listOf(
                    Departamento("1", "RH", "Departamento de Recursos Humanos"),
                    Departamento("2", "TI", "Departamento de Tecnologia da Informação"),
                    Departamento("3", "Financeiro", "Departamento Financeiro")
                )
            }

            DepartamentosScreen(
                departamentos = departamentos,
                onEdit = { departamentoId ->
                    // Lógica de navegação ou ação de edição
                    println("Editar departamento: $departamentoId")
                },
                onDelete = { departamentoId ->
                    // Lógica de exclusão ou navegação
                    println("Deletar departamento: $departamentoId")
                },
                onAdd = {
                    // Navegar para a tela de criação de departamento
                    navController.navigate("criar_departamento_screen")
                }
            )
        }

        composable("criar_departamento_screen") {
            CriarDepartamentoScreen(
                onBack = { navController.popBackStack() },
                onSubmit = { nome, descricao ->
                    // Lógica para cadastrar o departamento
                    println("Cadastrar departamento: $nome, $descricao")
                    // Voltar para a tela de departamentos
                    navController.popBackStack()
                }
            )
        }

    }
}

