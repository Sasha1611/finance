package com.example.mfinance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpentTypeComponent(color: Color) {
   Row(verticalAlignment = Alignment.CenterVertically) {
      Box(modifier = Modifier
         .background(color)
         .height(10.dp)
         .width(10.dp))
      Spacer(modifier = Modifier.width(5.dp))
      Text(text = "Store - 73%")
   }
}

@Preview(showBackground = true)
@Composable
private fun SpentTypeComponentPrev() {
   SpentTypeComponent(color = Color.Green)
}

@Preview(showBackground = true, widthDp = 450)
@Composable
private fun SpentTypeRowPrev() {
   Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
      SpentTypeComponent(color = Color.Green)
      SpentTypeComponent(color = Color.Red)
      SpentTypeComponent(color = Color.Yellow)
      SpentTypeComponent(color = Color.Blue)
   }
}