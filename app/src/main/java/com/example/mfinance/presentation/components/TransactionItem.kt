package com.example.mfinance.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mfinance.presentation.transaction.TransactionUIState
import com.example.mfinance.ui.theme.MFinanceTheme
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: TransactionUIState = getTestTransaction(),
) {
    val shape = RoundedCornerShape(8.dp)
    val iconColor = if (transaction.amount.toDouble() > 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
    val amountColor = if (transaction.amount.toDouble() > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(shape = shape, color = MaterialTheme.colorScheme.primaryContainer)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = if (transaction.amount.toDouble() > 0) Icons.Default.AttachMoney else Icons.Default.MoneyOff,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            tint = iconColor,
            contentDescription = ""
        )
        Column(Modifier.padding(start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = transaction.type,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
            Text(
                text = "${transaction.time.dayOfMonth} ${transaction.time.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${transaction.time.year}",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = transaction.amount,
            color = amountColor,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360)
fun PreviewOfTransactionItem() {
    MFinanceTheme {
        TransactionItem()
    }
}

fun getTestTransaction(): TransactionUIState {
    return TransactionUIState(1, "460.0", "Food", "Zabka", LocalDateTime.now())
}
