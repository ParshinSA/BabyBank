package com.example.babybank.common.extentions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.babybank.common.constants.SEPARATOR_COMMA
import com.example.babybank.common.constants.SEPARATOR_SPACE
import com.example.babybank.common.constants.SEPARATOR_SYSTEM_SPACE
import java.text.DecimalFormat

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply { arguments = Bundle().apply(action) }
}

fun Number.toStringMoneyFormat(): String {
    val formatter = DecimalFormat("###,###.##")
    val result = formatter.format(this)
        .replace(SEPARATOR_SYSTEM_SPACE, SEPARATOR_SPACE)
    return if (this.toInt().toDouble() == this) "$result${SEPARATOR_COMMA}00" else result
}

fun String.toNumberFormat(): Number? {
    if (this.isEmpty()) return null

    // если после запятой более двух символов, отрезаем лишнее
    val input =
        if (this.contains(SEPARATOR_COMMA) && this.indexOf(SEPARATOR_COMMA) < this.length - 3)
            this.substring(0, this.indexOf(SEPARATOR_COMMA) + 3)
        else this

    input.replace(SEPARATOR_SPACE, SEPARATOR_SYSTEM_SPACE)

    val formatter = DecimalFormat("###,###.##")
    return formatter.parse(input)
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun String.addSpaceStepFour(): CharSequence {
    var result = ""
    this.forEachIndexed { index, char ->
        result += if (index % 4 == 0) " $char" else char
    }
    return result
}