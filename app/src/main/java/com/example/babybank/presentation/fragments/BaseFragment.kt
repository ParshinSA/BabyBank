package com.example.babybank.presentation.fragments

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.babybank.R
import com.example.babybank.presentation.models.ItemClick

abstract class BaseFragment : Fragment(), ItemClick {

    //  Временно
    private var toast: Toast? = null
    private var errorDialog: AlertDialog? = null

    abstract fun inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun click(itemId: Int) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), "Click $itemId", Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun showErrorDialog(errorMessage: Int) {
        errorDialog = AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.titleErrorDialog))
            .setMessage(resources.getString(errorMessage))
            .create()
        // FIXME: 04.08.2022  переделать на класс, а может и нет )))
        errorDialog?.show()
    }

    override fun onDestroy() {
        Log.d("MyTAG", "onDestroy: ${this.javaClass.name} ")
        errorDialog?.dismiss()
        errorDialog = null
        toast?.cancel()
        toast = null
        super.onDestroy()
    }

}