package ru.virtual.feature_auth.presentation

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.virtual.feature_auth.domain.AuthUseCase
import ru.virtual.feature_auth.domain.Verified
import javax.inject.Inject

const val TIMER_FINISH_TIME = 30000L
const val TIMER_STEP_INTERVAL = 1000L

class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val countDownTimer: CountDownTimer = object : CountDownTimer(TIMER_FINISH_TIME, TIMER_STEP_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            timerIsRunning = true
            onTick?.invoke(millisUntilFinished)
        }

        override fun onFinish() {
            timerIsRunning = false
            onFinish?.invoke()
        }
    }

    private var timerIsRunning = false

    private var onTick: ((Long) -> Unit)? = null
    private var onFinish: (() -> Unit)? = null

    private val _isVerified = MutableLiveData<Verified>()
    val isVerified: LiveData<Verified> = _isVerified

    fun sendMessageToEmail(email: String) = compositeDisposable.add(authUseCase.sendCodeToEmail(email)
        .subscribe({}, {
            Log.d("asd", it.message.toString())
        })
    )

    fun checkCode(email: String, code: String) = compositeDisposable.add(authUseCase.checkCode(email, code)
        .subscribe({
            if(it.isVerified) { countDownTimer.cancel() }
           _isVerified.postValue(it)
        }, {})
    )

    fun sendFamilyInvite(email: String) = compositeDisposable.add(
        authUseCase.sendFamilyInvite(email)
            .subscribe({}, {})
    )

    fun setOnTimerTick(listener: (Long) -> Unit) { onTick = listener }

    fun setOnTimerFinish(listener: () -> Unit) { onFinish = listener }

    fun startTimer() {
        if (timerIsRunning) return
        countDownTimer.start()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}