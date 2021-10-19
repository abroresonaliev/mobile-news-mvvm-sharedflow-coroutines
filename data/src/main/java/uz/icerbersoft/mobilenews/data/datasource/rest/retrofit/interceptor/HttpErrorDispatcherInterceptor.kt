package uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import uz.icerbersoft.mobilenews.data.BuildConfig
import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.exception.RestErrorException
import java.net.ConnectException

internal class HttpErrorDispatcherInterceptor private constructor(
    private val handlers: Array<HttpErrorResponseHandler>,
    private val mappers: Array<HttpErrorResponseMapper>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response?

        try {
            response = chain.proceed(chain.request())
        } catch (exception: Exception) {
            if (BuildConfig.DEBUG) Log.d(TAG, "Error when proceeding", exception)
            throw ConnectException()
        }

        val httpErrorCode: Int = response.code

        if (httpErrorCode in 400..503) {

            val errorResponse: HttpErrorResponse = mappers.mapNotNull {
                it.onHandleResponse(httpErrorCode, response?.peekBody(Long.MAX_VALUE)?.string())
            }.firstOrNull() ?: getErrorResponse(httpErrorCode)

            val firstAvailableErrorHandler: Throwable? = handlers.mapNotNull {
                it.onHandleError(httpErrorCode, errorResponse)
            }.firstOrNull()

            if (firstAvailableErrorHandler != null) throw firstAvailableErrorHandler
            else throw RestErrorException(errorResponse)
        }

        return response
    }

    private fun getErrorResponse(httpErrorCode: Int): HttpErrorResponse =
        object : HttpErrorResponse {
            override val httpErrorCode: Int = httpErrorCode
        }

    companion object {
        private const val TAG: String = "Error Interceptor"

        class Builder {
            private val handlers: MutableList<HttpErrorResponseHandler> = arrayListOf()
            private val mappers: MutableList<HttpErrorResponseMapper> = arrayListOf()

            fun setResponseHandlers(vararg handlers: HttpErrorResponseHandler): Builder {
                this.handlers.apply { clear(); addAll(handlers) }
                return this
            }

            fun setResponseMappers(vararg mappers: HttpErrorResponseMapper): Builder {
                this.mappers.apply { clear(); addAll(mappers) }
                return this
            }

            fun build(): HttpErrorDispatcherInterceptor =
                HttpErrorDispatcherInterceptor(
                    handlers = handlers.toTypedArray(),
                    mappers = mappers.toTypedArray()
                )
        }
    }
}