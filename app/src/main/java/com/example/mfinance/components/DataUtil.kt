package com.example.mfinance.components

import java.text.DecimalFormat

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}
private val AmountDecimalFormat = DecimalFormat("#,###.##")