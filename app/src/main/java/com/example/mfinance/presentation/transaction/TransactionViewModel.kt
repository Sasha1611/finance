package com.example.mfinance.presentation.transaction

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mfinance.data.TransactionEntity
import com.example.mfinance.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TransactionViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUIState())
        private set

    private val _transactionsFlow = MutableStateFlow<List<TransactionUIState>>(emptyList())

    private val transactionsFlow: StateFlow<List<TransactionUIState>> = _transactionsFlow

    init {
        viewModelScope.launch {
            transactionRepository.getAllTransactions()
                .collect { transactionList ->
                    val uiStateList = transactionList.map { it.toTransactionUI() }
                    _transactionsFlow.value = uiStateList
                }
        }
    }

    suspend fun saveTransaction() {
        transactionUiState.type = "Service"
        transactionUiState.amount = 200.0
        transactionUiState.time = LocalDateTime.now()
        transactionUiState.category = "Spotify"
        transactionRepository.insertTransaction(transactionUiState.toTransactionEntity())
    }

    suspend fun deleteTransaction() {
        transactionRepository.deleteTransaction(transactionUiState.toTransactionEntity())
    }

    suspend fun updateTransaction(){
        transactionRepository.updateTransaction(transactionUiState.toTransactionEntity())
    }

    fun getAllTransaction() = transactionsFlow


    fun TransactionUIState.toTransactionEntity(): TransactionEntity =
        TransactionEntity(
            id = this.id,
            amount = this.amount,
            type = this.type,
            category = this.category,
            time = this.time
        )

    fun TransactionEntity.toTransactionUI(): TransactionUIState =
        TransactionUIState(
            id = this.id,
            amount = this.amount,
            type = this.type,
            category = this.category,
            time = this.time
        )
}