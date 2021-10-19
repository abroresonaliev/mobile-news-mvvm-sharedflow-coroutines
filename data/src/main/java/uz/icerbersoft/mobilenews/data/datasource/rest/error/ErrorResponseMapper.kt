package uz.icerbersoft.mobilenews.data.datasource.rest.error

import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor.HttpErrorResponseMapper

internal class ErrorResponseMapper : HttpErrorResponseMapper {
    override fun onHandleResponse(httpErrorCode: Int, response: String?) =
        null
}