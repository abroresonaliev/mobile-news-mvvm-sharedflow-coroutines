package uz.icerbersoft.mobilenews.data.datasource.rest.error

import uz.icerbersoft.mobilenews.data.datasource.rest.exception.*
import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor.HttpErrorResponse
import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor.HttpErrorResponseHandler
import java.io.IOException
import java.net.HttpURLConnection

internal class ErrorResponseHandler : HttpErrorResponseHandler {

    override fun onHandleError(
        httpErrorCode: Int, errorResponse: HttpErrorResponse
    ): IOException? = when (httpErrorCode) {
        HttpURLConnection.HTTP_BAD_REQUEST ->
            throw BadRequestException(errorResponse)
        HttpURLConnection.HTTP_UNAUTHORIZED ->
            throw UnauthorizedException(errorResponse)
        HttpURLConnection.HTTP_PAYMENT_REQUIRED ->
            throw PaymentRequiredException(errorResponse)
        HttpURLConnection.HTTP_FORBIDDEN ->
            throw ForbiddenException(errorResponse)
        HttpURLConnection.HTTP_NOT_FOUND ->
            throw NotFoundException(errorResponse)
        HttpURLConnection.HTTP_INTERNAL_ERROR ->
            throw InternalServerErrorException(errorResponse)
        HttpURLConnection.HTTP_UNAVAILABLE ->
            throw ServiceUnavailableException(errorResponse)
        else -> null
    }
}