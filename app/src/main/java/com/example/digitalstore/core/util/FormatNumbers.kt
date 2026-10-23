package com.example.digitalstore.core.util
import java.util.Locale

fun Int.formatThousands(): String {
    return String.format(Locale("pt", "BR"), "%,d", this)
}

fun Double.formatDecimalCurrency(): String {
    return String.format(Locale("pt", "BR"), "%,.2f", this)
}