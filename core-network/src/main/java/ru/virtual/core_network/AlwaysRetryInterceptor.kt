package ru.virtual.core_network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AlwaysRetryInterceptor(private val maxAttempts: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response: Response?
        var lastException: IOException? = null
        for (attempt in 0 until maxAttempts) {
            try {
                response = chain.proceed(request)
                return response
            } catch (e: IOException) {
                lastException = e
            }
        }
        throw lastException!!
    }
}
