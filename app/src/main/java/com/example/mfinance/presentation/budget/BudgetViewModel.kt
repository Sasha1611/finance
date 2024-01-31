package com.example.mfinance.presentation.budget


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mfinance.data.BudgetEntity
import com.example.mfinance.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class BudgetViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    private var _budgetFlow = MutableStateFlow(BudgetUiState())

    val budgetFlow: StateFlow<BudgetUiState> = _budgetFlow

    init {
        val now = LocalDateTime.now()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                budgetRepository.getBudgetFor(now.year, now.monthValue)
            }
            result.collect { budgetEntity ->
                budgetEntity?.let {
                    _budgetFlow.value = it.toBudgetUiState()
                }
            }
        }
    }

    fun updateBudgetUiState(budgetUiState: BudgetUiState){
        _budgetFlow.value = budgetUiState
    }

    suspend fun saveBudget(){
        budgetRepository.insert(_budgetFlow.value.toBudgetEntity())
    }

    fun BudgetUiState.toBudgetEntity(): BudgetEntity {
        return BudgetEntity(
            id = this.id,
            amount = this.amount,
            amountToSave = this.amountToSave,
            year = this.year,
            month = this.month
        )
    }

    fun BudgetEntity.toBudgetUiState(): BudgetUiState {
        return BudgetUiState(
            id = this.id,
            amount = this.amount,
            amountToSave = this.amountToSave,
            year = this.year,
            month = this.month
        )
    }
}