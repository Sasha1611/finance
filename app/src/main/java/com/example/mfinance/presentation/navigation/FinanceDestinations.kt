package com.example.mfinance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

interface Screen {
    val route: String
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

object Transaction : Screen {
    override val route: String = "transaction"
    override val selectedIcon: ImageVector = Icons.Default.Home
    override val unselectedIcon: ImageVector = Icons.Filled.Home
}

object Details : Screen {
    override val route: String = "details"
    override val selectedIcon: ImageVector = Icons.Default.Details
    override val unselectedIcon: ImageVector = Icons.Filled.Details
}

object Budget : Screen {
    override val route: String = "budget"
    override val selectedIcon: ImageVector = Icons.Default.Edit
    override val unselectedIcon: ImageVector = Icons.Filled.Edit
}

fun getListOfScreen():List<Screen>{
    return listOf(Transaction, Details, Budget)
}