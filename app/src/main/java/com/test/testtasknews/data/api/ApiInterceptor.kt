package com.test.testtasknews.data.api

import com.test.testtasknews.constant.Api
import com.test.testtasknews.exception.NoConnectivityException
import com.test.testtasknews.provider.InternetConnectionChecker
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor constructor(private val internetConnectionChecker: InternetConnectionChecker) :
    Interceptor {

    @Throws(NoConnectivityException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!internetConnectionChecker.isNetworkAvailable()) throw NoConnectivityException()
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader(Api.CONTENT_TYPE_HEADER, Api.APPLICATION_JSON_HEADER_VALUE)
            .url(
                request
                    .url()
                    .newBuilder()
                    .addQueryParameter(Api.API_KEY_HEADER, Api.API_KEY)
                    .build()
            )
            .build()
        return chain.proceed(request)
    }
}