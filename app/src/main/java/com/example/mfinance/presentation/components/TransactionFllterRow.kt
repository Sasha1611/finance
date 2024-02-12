package com.example.mfinance.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.transaction.TransactionViewModel
import com.example.mfinance.presentation.transaction.TransactionViewModel.FilterUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFilterRow(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onApplyFilterClick: (FilterUiState) -> Unit = {}
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var bottomSheetContent: @Composable () -> Unit by remember { mutableStateOf({}) }
    val filterUiState = viewModel.filterUiState.collectAsStateWithLifecycle().value
    val categories = viewModel.categoriesFlow.collectAsStateWithLifecycle().value
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            AmountChip(onClick = {
                bottomSheetContent = {
                    AmountBottomSheetContent(filterUiState = { filterUiState }, onApplyClick = {
                        isBottomSheetVisible = false
                        onApplyFilterClick(it)
                    })
                }
                isBottomSheetVisible = true
            })
        }
        item {
            CategoryChip(onClick = {
                bottomSheetContent = {
                    CategoryBottomSheetContent(
                        filterUiState = { filterUiState },
                        categories = {categories},
                        onApplyClick = {
                            isBottomSheetVisible = false
                            onApplyFilterClick(it)
                        }
                    )
                }
                isBottomSheetVisible = true
            })
        }
        item {
            DateChip(onClick = {
                bottomSheetContent = {
                    DateBottomSheetContent(filterUiState = { filterUiState }, onApplyClick = {
                        isBottomSheetVisible = false
                        onApplyFilterClick(it)
                    })
                }
                isBottomSheetVisible = true
            })
        }
        item {
            FilterChip(onClick = {
                bottomSheetContent = { Text(text = "F") }
                isBottomSheetVisible = true
            })
        }
    }
    if (isBottomSheetVisible) {
        val modalBottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            sheetState = modalBottomSheetState,
            onDismissRequest = {
                isBottomSheetVisible = false
            }
        ) {
            bottomSheetContent()
        }
    }
}
