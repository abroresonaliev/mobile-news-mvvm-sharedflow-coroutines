package uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor

internal interface HttpErrorResponseMapper : HttpErrorDispatcherComponent {

    fun onHandleResponse(
        httpErrorCode: Int,
        response: String?
    ): HttpErrorResponse?
}