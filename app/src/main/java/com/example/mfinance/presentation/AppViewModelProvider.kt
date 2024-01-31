package com.example.mfinance.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mfinance.presentation.budget.BudgetViewModel
import com.example.mfinance.presentation.transaction.TransactionViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TransactionViewModel(mFinanceApplication().container.transactionRepository)
        }
        initializer {
            BudgetViewModel(mFinanceApplication().container.budgetRepository)
        }
    }
}
fun CreationExtras.mFinanceApplication(): MobileFinanceApp{
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MobileFinanceApp)
}
