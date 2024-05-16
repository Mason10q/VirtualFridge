package ru.virtual.core_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State

abstract class StateViewModel<S: State, E: Effect> : ViewModel() {

    protected val _state = MutableLiveData<S>()
    val state: LiveData<S> = _state

    protected val _effect = SingleLiveEvent<E>()
    val effect: LiveData<E> = _effect
}