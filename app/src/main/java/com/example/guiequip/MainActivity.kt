package com.example.guiequip

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.guiequip.navigation.NavGraph
import com.example.guiequip.ui.theme.GuiequipTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            GuiequipTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    NavGraph(navController = navController)
                }
            }
        }
    }
}
