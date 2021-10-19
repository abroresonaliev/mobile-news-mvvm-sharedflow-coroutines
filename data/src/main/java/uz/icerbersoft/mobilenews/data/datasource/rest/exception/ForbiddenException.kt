package uz.icerbersoft.mobilenews.data.datasource.rest.exception

import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.exception.RestErrorException
import uz.icerbersoft.mobilenews.data.datasource.rest.retrofit.interceptor.HttpErrorResponse

class ForbiddenException(
    override val response: HttpErrorResponse
) : RestErrorException(response)