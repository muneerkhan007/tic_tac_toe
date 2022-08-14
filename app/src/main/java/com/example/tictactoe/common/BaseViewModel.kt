package com.example.tictactoe.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<viewState: BaseViewState>: ViewModel() {
    abstract val initialState: viewState
    private val _observableState: MutableLiveData<viewState> = MutableLiveData()

    val observableState: LiveData<viewState> = _observableState

    var state : viewState
        get() = _observableState.value?:initialState
        set(value) = _observableState.setValue(value)
    var stateAsync: viewState
        get() = state
        set(value) =_observableState.postValue(value)
}