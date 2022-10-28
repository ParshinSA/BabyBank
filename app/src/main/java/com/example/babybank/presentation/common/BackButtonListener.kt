package com.example.babybank.presentation.common

/**
 * author: ParshinSA
 * email: ParshinSA@msk.bcs.ru
 * specification: Classes that implement this interface handle pressing the back button on the device
 * */
interface BackButtonListener {
    fun onBackPressed(): Boolean
}