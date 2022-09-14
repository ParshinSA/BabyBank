package com.example.babybank.common.extentions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply { arguments = Bundle().apply(action) }
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun String.addSpaceStepFour(): String {
    var result = ""
    this.forEachIndexed { index, char ->
        result += if (index % 4 == 0) " $char" else char
    }
    return result
}