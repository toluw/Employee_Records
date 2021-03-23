package com.up.employee.data.remote

import com.up.employee.data.model.GetEmployee
import com.up.employee.data.model.Printful
import retrofit2.http.GET

interface PrintfulApi {
    @GET("countries")
    suspend fun getPrintful() : Printful
}