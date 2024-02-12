package com.example.mfinance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mfinance.presentation.screen.TransactionEntryScreen
import com.example.mfinance.presentation.screen.BudgetScreen
import com.example.mfinance.presentation.screen.DetailsScreen
import com.example.mfinance.presentation.screen.TransactionScreen

@Composable
fun FinanceNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Transaction.route,
        modifier = modifier
    ) {
        composable(Transaction.route) {
            TransactionScreen(onAddClick = {
                navController.navigateSingleTopTo(
                    TransactionEntry.route
                )
            })
        }
        composable(Details.route) { DetailsScreen() }
        composable(Budget.route) { BudgetScreen() }
        composable(TransactionEntry.route) {
            TransactionEntryScreen(
                navigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

