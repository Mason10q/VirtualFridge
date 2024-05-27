package ru.virtual.feature_auth.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import ru.virtual.feature_auth.data.AuthRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
): AuthUseCase {

    override fun sendCodeToEmail(email: String): Completable = repository.sendCodeToEmail(email)
        .observeOn(AndroidSchedulers.mainThread())

    override fun checkCode(email: String, code: String) = repository.checkCode(email, code)
        .map { Verified(it.isVerified ?: false, it.familyId ?: -1) }
        .observeOn(AndroidSchedulers.mainThread())

}