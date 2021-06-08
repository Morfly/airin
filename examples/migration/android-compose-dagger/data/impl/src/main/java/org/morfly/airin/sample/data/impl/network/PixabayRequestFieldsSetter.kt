package org.morfly.airin.sample.data.impl.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class PixabayRequestFieldsSetter @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    private companion object {
        const val API_KEY = "20158920-274a1e484842c9ef690adf8e0"
    }
}