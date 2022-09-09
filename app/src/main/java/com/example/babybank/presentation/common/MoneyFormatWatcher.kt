package com.example.babybank.presentation.common

import android.text.Editable
import android.text.TextWatcher
import com.example.babybank.common.constants.*
import com.example.babybank.common.extentions.toNumberFormat
import com.example.babybank.common.extentions.toStringMoneyFormat
import io.reactivex.subjects.BehaviorSubject

class MoneyFormatWatcher : TextWatcher {
    private var validFormatMoney = "0"
    private val userInputAmountMoney = BehaviorSubject.create<String>()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(userInput: Editable) {
        val startStr = userInput.toString()

        // пропускаем только цифры и сепараторы
        // точки и системные пробелы заменяем на валидные сепараторы
        var digitsDotsSpace = startStr.filter { char: Char ->
            (Character.isDigit(char) || char in arrayOf(
                SEPARATOR_COMMA,
                SEPARATOR_SPACE,
                SEPARATOR_DOT,
                SEPARATOR_SYSTEM_SPACE
            ))
        }
            .replace(SEPARATOR_DOT, SEPARATOR_COMMA)
            .replace(SEPARATOR_SYSTEM_SPACE, SEPARATOR_SPACE)

        // считаем количество запятых(разделителей)
        // в случае если их больше одной используем прошлый валидный вариант validFormatMoney
        val countDots = digitsDotsSpace.count { char: Char -> char == SEPARATOR_COMMA }
        if (countDots > 1) digitsDotsSpace = validFormatMoney

        // если последний символ SEPARATOR_COMMA,
        // значит пользователь вводит дробную часть форматируем и добавляем запятую
        validFormatMoney =
            if (digitsDotsSpace.isNotEmpty() && digitsDotsSpace.last() == SEPARATOR_COMMA) {
                digitsDotsSpace
            } else {
                var numberFormat = digitsDotsSpace.toNumberFormat() ?: 0
                if (numberFormat.toDouble() > MAX_TRANSFER_MONEY) numberFormat = MAX_TRANSFER_MONEY
                if (numberFormat == 0) "" else numberFormat.toStringMoneyFormat()
            }

        // отправляем валидный вариант слушателям
        userInputAmountMoney.onNext(validFormatMoney)

        // если "ввод" и валидный вариант одинаковы не меняем "ввод"
        if (startStr != validFormatMoney) {
            userInput.replace(0, startStr.length, validFormatMoney)
        }
    }

    fun getUserInputAmountMoney() = userInputAmountMoney
}
