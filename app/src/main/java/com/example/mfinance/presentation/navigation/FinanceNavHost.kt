package com.example.mfinance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable(Transaction.route) { TransactionScreen() }
        composable(Details.route) { DetailsScreen() }
        composable(Budget.route) { BudgetScreen() }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

