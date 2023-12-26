package com.example.mfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mfinance.components.BottomNav
import com.example.mfinance.navigation.FinanceNavHost
import com.example.mfinance.ui.theme.MFinanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MFinanceTheme {
                val navigationController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNav(navigationController)
                    }
                ) { innerPadding ->
                   FinanceNavHost(
                       navController = navigationController,
                       modifier = Modifier.padding(innerPadding)
                   )
                }
            }
        }
    }
}

