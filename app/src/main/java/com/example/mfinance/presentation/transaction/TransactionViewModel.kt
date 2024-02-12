package com.example.mfinance.presentation.transaction

import androidx.compose.runtime.Immutable
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

    private var transactionUiState by mutableStateOf(TransactionUIState())

    var filterUiState = MutableStateFlow(FilterUiState())
        private set

    private val _transactionsFlow = MutableStateFlow<List<TransactionUIState>>(emptyList())

    private val transactionsFlow: StateFlow<List<TransactionUIState>> = _transactionsFlow

    private val _categoriesFlow = MutableStateFlow<List<String>>(emptyList())

    val categoriesFlow: StateFlow<List<String>> = _categoriesFlow

    private val _totalAmount = MutableStateFlow(0.0)

    val totalAmount: MutableStateFlow<Double> = _totalAmount

    init {
        val firstAndLastDayOfCurrentMonth = getFirstAndLastDayOfCurrentMonth()
        updateTransactionsWithNewFilter(filterUiState.value)
        getFilteredSpentBetween(filterUiState.value)
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
    }

    fun updateTransactionUiState(updatedTransactionUIState: TransactionUIState) {
        transactionUiState = updatedTransactionUIState
    }

    fun updateTransactionsWithNewFilter(newFilterUiState: FilterUiState) {
        filterUiState.value = newFilterUiState
        if (filterUiState.value.categories.isEmpty()) {
            setTransactionsWithNotCompleteFilter()
        } else {
            setTransactionsWithFilter()
        }
        getFilteredSpentBetween(filterUiState.value)
    }

    private fun setTransactionsWithFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getFilteredTransactions(
                    amountTo = filterUiState.value.amountTo,
                    amountFrom = filterUiState.value.amountFrom,
                    categories = filterUiState.value.categories,
                    timeTo = filterUiState.value.timeTo,
                    timeFrom = filterUiState.value.timeFrom
                )
            }
            result.collect { transactionList ->
                _transactionsFlow.value = transactionList.map { it.toTransactionUI() }
            }
        }
    }

    private fun setTransactionsWithNotCompleteFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getFilteredTransactionsWithEmptyCategories(
                    amountTo = filterUiState.value.amountTo,
                    amountFrom = filterUiState.value.amountFrom,
                    timeTo = filterUiState.value.timeTo,
                    timeFrom = filterUiState.value.timeFrom
                )
            }
            result.collect { transactionList ->
                _transactionsFlow.value = transactionList.map { it.toTransactionUI() }
            }
        }
    }

    private fun getFilteredSpentBetween(filterUiState: FilterUiState) {
        if (filterUiState.categories.isEmpty()) {
            setTotalSpentBetweenWithNotCompleteFilter()
        } else {
            setTotalSpentBetweenWithCompleteFilter()
        }
    }

    private fun setTotalSpentBetweenWithNotCompleteFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getTotalSpentBetweenWithEmptyCategories(
                    amountTo = filterUiState.value.amountTo,
                    amountFrom = filterUiState.value.amountFrom,
                    timeTo = filterUiState.value.timeTo,
                    timeFrom = filterUiState.value.timeFrom
                )
            }
            result.collect {
                _totalAmount.value = it
            }
        }
    }

    private fun setTotalSpentBetweenWithCompleteFilter() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                transactionRepository.getTotalSpentBetweenWithNotEmptyCategories(
                    amountTo = filterUiState.value.amountTo,
                    amountFrom = filterUiState.value.amountFrom,
                    timeTo = filterUiState.value.timeTo,
                    timeFrom = filterUiState.value.timeFrom,
                    categories = filterUiState.value.categories,
                )
            }
            result.collect {
                _totalAmount.value = it
            }
        }
    }


    suspend fun saveTransaction(newTransactionUIState: TransactionUIState) {
        transactionRepository.insertTransaction(newTransactionUIState.toTransactionEntity())
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

    @Immutable
    data class FilterUiState(
        val amountFrom: Long = 0,
        val amountTo: Long = 0,
        val categories: List<String> = emptyList(),
        val timeFrom: Long = getFirstAndLastDayOfCurrentMonth().first,
        val timeTo: Long = getFirstAndLastDayOfCurrentMonth().second
    )
}