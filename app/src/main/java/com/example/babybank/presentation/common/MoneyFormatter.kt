package com.example.babybank.presentation.common

import com.example.babybank.common.constants.SEPARATOR_COMMA
import com.example.babybank.common.constants.SEPARATOR_SPACE
import com.example.babybank.common.constants.SEPARATOR_SYSTEM_SPACE
import java.text.DecimalFormat
import javax.inject.Inject

class MoneyFormatter @Inject constructor() {

    private val formatter = DecimalFormat("###,###.##")

    fun toStringMoneyFormat(number: Number): String {
        return formatter.format(number).replace(SEPARATOR_SYSTEM_SPACE, SEPARATOR_SPACE)
    }

    fun toNumberMoneyFormat(string: String): Number? {
        if (string.isEmpty()) return null

        // если после запятой более двух символов, отрезаем лишнее
        val input =
            if (string.contains(SEPARATOR_COMMA) && string.indexOf(SEPARATOR_COMMA) < string.length - 3)
                string.substring(0, string.indexOf(SEPARATOR_COMMA) + 3)
            else string

        input.replace(SEPARATOR_SPACE, SEPARATOR_SYSTEM_SPACE)
        return formatter.parse(input)
    }

}