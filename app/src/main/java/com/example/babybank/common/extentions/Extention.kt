package com.example.babybank.common.extentions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply { arguments = Bundle().apply(action) }
}

fun Double.toStringMoneyFormat(): String {
    val formatter = DecimalFormat("###,###.##")
    val result = formatter.format(this)
        .replace(",", ".")
        .replace(" ", ",") //   -> системный пробел, подставляемый при первой конвертации
    return if (this.toInt().toDouble() == this) "$result.00" else result
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}