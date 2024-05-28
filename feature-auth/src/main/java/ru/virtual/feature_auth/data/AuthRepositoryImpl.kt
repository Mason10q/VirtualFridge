package ru.virtual.feature_auth.data

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_network.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val familyId: Int
): AuthRepository {

    override fun sendCodeToEmail(email: String): Completable = api.sendCodeToEmail(email)
        .subscribeOn(Schedulers.io())

    override fun checkCode(email: String, code: String) = api.checkCode(email, code)
        .subscribeOn(Schedulers.io())

    override fun sendFamilyInvite(email: String): Completable = api.sendInvite(email, familyId)
        .subscribeOn(Schedulers.io())

}