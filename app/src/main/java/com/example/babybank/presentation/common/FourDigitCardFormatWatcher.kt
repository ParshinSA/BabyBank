package com.example.babybank.presentation.common

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.babybank.common.extentions.addSpaceStepFour
import io.reactivex.subjects.BehaviorSubject

class FourDigitCardFormatWatcher : TextWatcher {
    private val userInputCardNumber = BehaviorSubject.create<String>()

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(usInput: Editable) {
        val startStr = usInput.toString()
        val sixteenDigits = startStr.filter { char: Char -> Character.isDigit(char) }.take(16)
        val uiFormatCardNumber = sixteenDigits.addSpaceStepFour()

        Log.d("TAG", "afterTextChanged: $startStr, $uiFormatCardNumber")

        userInputCardNumber.onNext(sixteenDigits)

        if (startStr != uiFormatCardNumber) usInput.replace(0, startStr.length, uiFormatCardNumber)
    }

    fun getUserInputCardNumber() = userInputCardNumber

}