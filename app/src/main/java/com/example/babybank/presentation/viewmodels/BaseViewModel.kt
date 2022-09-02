package com.example.babybank.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babybank.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val errorMessageMutLiveData = MutableLiveData<Int>()
    val errorMessageLiveData: LiveData<Int> get() = errorMessageMutLiveData

    fun showErrorMessage() {
        errorMessageMutLiveData.value = R.string.errorMessage
    }

    fun Disposable.autoClear() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        Log.d("MyTAG", "onCleared: ${this.javaClass.name.takeLastWhile { it=='.' }} ")
        super.onCleared()
    }
}