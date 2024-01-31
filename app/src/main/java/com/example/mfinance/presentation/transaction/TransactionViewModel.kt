package com.example.mfinance.presentation.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mfinance.data.TransactionEntity
import com.example.mfinance.repository.TransactionRepository
import com.example.mfinance.util.getFirstAndLastDayOfCurrentMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUIState())
        private set

    var filterUiState by mutableStateOf(FilterUiState())
        private set

    private val _transactionsFlow = MutableStateFlow<List<TransactionUIState>>(emptyList())

    private val transactionsFlow: StateFlow<List<TransactionUIState>> = _transactionsFlow

    private val _categoriesFlow = MutableStateFlow<List<String>>(emptyList())

    val categoriesFlow: StateFlow<List<String>> = _categoriesFlow

    private val _totalAmount = MutableStateFlow(0.0)

    val totalAmount: MutableStateFlow<Double> = _totalAmount

    init {
        val firstAndLastDayOfCurrentMonth = getFirstAndLastDayOfCurrentMonth()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getAllTransactionsBetween(
                    firstAndLastDayOfCurrentMonth.first,
                    firstAndLastDayOfCurrentMonth.second
                )
            }
            result.collect { transactionList ->
                val uiStateList = transactionList.map { it.toTransactionUI() }
                _transactionsFlow.value = uiStateList
            }
        }
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getDistinctCategoriesBetween(
                    firstAndLastDayOfCurrentMonth.first,
                    firstAndLastDayOfCurrentMonth.second
                )
            }
            result.collect {
                _categoriesFlow.value = it
            }
        }
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getTotalSpentBetween(
                    firstAndLastDayOfCurrentMonth.first,
                    firstAndLastDayOfCurrentMonth.second
                )
            }
            result.collect {
                _totalAmount.value = it
            }
        }
    }

    fun updateTransactionUiState(updatedTransactionUIState: TransactionUIState) {
        transactionUiState = updatedTransactionUIState
    }

    fun updateFilterUiState(newFilterUiState: FilterUiState) {
        filterUiState = newFilterUiState
        if (filterUiState.categories.isEmpty()){
            refreshTransactionsWithNotCompleteFilter()
        }else{
            refreshTransactionsWithFilter()
        }
    }

    private fun refreshTransactionsWithFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getFilteredTransactions(
                    amountTo = filterUiState.amountTo,
                    amountFrom = filterUiState.amountFrom,
                    categories = filterUiState.categories,
                    timeTo = filterUiState.timeTo,
                    timeFrom = filterUiState.timeFrom
                )
            }
            result.collect { transactionList ->
                _transactionsFlow.value = transactionList.map { it.toTransactionUI() }
            }
        }
    }

    private fun refreshTransactionsWithNotCompleteFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getFilteredTransactionsWithEmptyCategories(
                    amountTo = filterUiState.amountTo,
                    amountFrom = filterUiState.amountFrom,
                    timeTo = filterUiState.timeTo,
                    timeFrom = filterUiState.timeFrom
                )
            }
            result.collect { transactionList ->
                _transactionsFlow.value = transactionList.map { it.toTransactionUI() }
            }
        }
    }

    suspend fun saveTransaction() {
        transactionRepository.insertTransaction(transactionUiState.toTransactionEntity())
    }

    suspend fun deleteTransaction() {
        transactionRepository.deleteTransaction(transactionUiState.toTransactionEntity())
    }

    suspend fun updateTransaction() {
        transactionRepository.updateTransaction(transactionUiState.toTransactionEntity())
    }

    fun getAllTransaction() = transactionsFlow


    private fun TransactionUIState.toTransactionEntity(): TransactionEntity =
        TransactionEntity(
            id = this.id,
            amount = this.amount.toDoubleOrNull() ?: 0.0,
            name = this.type,
            category = this.category,
            time = this.time
        )

    private fun TransactionEntity.toTransactionUI(): TransactionUIState =
        TransactionUIState(
            id = this.id,
            amount = this.amount.toString(),
            type = this.name,
            category = this.category,
            time = this.time
        )

    data class FilterUiState(
        val amountFrom: Int = 0,
        val amountTo: Int = 0,
        val categories: List<String> = emptyList(),
        val timeFrom: Long = 0,
        val timeTo: Long = 0
    )
}