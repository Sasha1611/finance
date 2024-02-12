package com.example.mfinance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

interface Screen{
    val route: String
}

interface ScreenWithIcon : Screen{
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

object Transaction : ScreenWithIcon {
    override val route: String = "transaction"
    override val selectedIcon: ImageVector = Icons.Default.Home
    override val unselectedIcon: ImageVector = Icons.Filled.Home
}

object Details : ScreenWithIcon {
    override val route: String = "details"
    override val selectedIcon: ImageVector = Icons.Default.Details
    override val unselectedIcon: ImageVector = Icons.Filled.Details
}

object Budget : ScreenWithIcon {
    override val route: String = "budget"
    override val selectedIcon: ImageVector = Icons.Default.Edit
    override val unselectedIcon: ImageVector = Icons.Filled.Edit
}

object TransactionEntry : Screen {
    override val route: String = "transactionEntry"
}

@Stable
fun getListOfScreen():List<ScreenWithIcon>{
    return listOf(Transaction, Details, Budget)
}