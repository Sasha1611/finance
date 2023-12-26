package com.example.mfinance.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mfinance.R

@Composable
fun BudgetCard() {
    LazyRow(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { TotalSpendCard() }
        item { TrendCard() }
        item { GoalCard() }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 600)
@Composable
fun BudgetCardPreview() {
    BudgetCard()
}

@Composable
fun TotalSpendCard() {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.total_spend),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "1400",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun TrendCard() {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Day spent trend",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "143",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = Icons.Default.TrendingDown,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun GoalCard() {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Goal to save",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "143",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}