package uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor

import okhttp3.OkHttpClient

internal fun OkHttpClient.Builder.withHttpErrorDispatcher(
    vararg components: HttpErrorDispatcherComponent
): OkHttpClient.Builder = addInterceptor(
    HttpErrorDispatcherInterceptor.Companion.Builder()
        .setResponseHandlers(
            *components.filterIsInstance<HttpErrorResponseHandler>().toTypedArray()
        )
        .setResponseMappers(*components.filterIsInstance<HttpErrorResponseMapper>().toTypedArray())
        .build()
)