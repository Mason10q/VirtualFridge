package ru.virtual.core_android

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State

abstract class RxStateViewModel<S: State, E: Effect> : StateViewModel<S, E>() {

    private val compositeDisposable = CompositeDisposable()

    protected fun invokeDisposable(block: () -> Disposable) = compositeDisposable.add(block())

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}