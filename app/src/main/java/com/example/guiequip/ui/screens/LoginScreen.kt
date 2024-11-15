package com.example.guiequip.ui.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.guiequip.R
import com.example.guiequip.auth.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(navController: NavController) {
    val authRepository = AuthRepository()

    val context = LocalContext.current
    val activity = context as? Activity

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                authRepository.firebaseAuthWithGoogle(idToken) { isSuccessful ->
                    if (isSuccessful) {
                        navController.navigate("home_screen")
                    } else {
                        Log.e("Login", "Authentication failed")
                    }
                }
            }
        } catch (e: ApiException) {
            Log.e("Login", "Google sign-in failed: ${e.message}")
        }
    }

    val signIn = {
        Log.d("LoginScreen", "Starting Google Sign-In...")
        val signInClient = authRepository.getGoogleSignInClient(activity!!)
        val signInIntent = signInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(256.dp)
            )

            Text(
                text = "Organize e gerencie todos os seus equipamentos em um só lugar!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )

            TextButton(
                onClick = { signIn() },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
            ) {
                Text(
                    text = "Entrar com Google",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}
