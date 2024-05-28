package ru.virtual.feature_auth.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_network.dto.VerifiedDto

interface AuthRepository {

    fun sendCodeToEmail(email: String): Completable

    fun checkCode(email: String, code: String): Single<VerifiedDto>

    fun sendFamilyInvite(email: String): Completable

}