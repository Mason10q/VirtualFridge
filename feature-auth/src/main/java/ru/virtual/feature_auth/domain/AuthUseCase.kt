package ru.virtual.feature_auth.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface AuthUseCase {

    fun sendCodeToEmail(email: String): Completable

    fun checkCode(email: String, code: String): Single<Verified>

}