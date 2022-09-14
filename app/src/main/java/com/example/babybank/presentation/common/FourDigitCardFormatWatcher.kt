package com.example.babybank.presentation.common

import android.text.Editable
import android.text.TextWatcher
import com.example.babybank.common.extentions.addSpaceStepFour
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class FourDigitCardFormatWatcher @Inject constructor() : TextWatcher {

    private val userInputCardNumber = BehaviorSubject.create<String>()

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(usInput: Editable) {
        val startStr = usInput.toString()

        val sixteenDigits = startStr
            .filter { char: Char -> Character.isDigit(char) }
            .take(MAX_NUMBER_DIGITS)

        val uiFormatCardNumber = sixteenDigits.addSpaceStepFour()
        userInputCardNumber.onNext(uiFormatCardNumber)

        if (startStr != uiFormatCardNumber) usInput.replace(0, startStr.length, uiFormatCardNumber)
    }

    fun getUserInputCardNumber(): Observable<String> = userInputCardNumber.hide()

    companion object {
        const val MAX_NUMBER_DIGITS = 16
    }
}