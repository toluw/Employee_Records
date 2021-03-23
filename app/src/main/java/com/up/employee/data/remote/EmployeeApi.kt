package com.up.employee.data.remote

import com.up.employee.data.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EmployeeApi {

    @FormUrlEncoded
    @POST("admin_signup.php")
    suspend fun admin_signup(@Field("firstname") firstname: String,
                             @Field("lastname") lastname: String,
                             @Field("gender") gender: String,
                             @Field("dob") dob: String,
                             @Field("address") address: String,
                             @Field("country") country: String,
                             @Field("username") username: String,
                             @Field("password") password: String,
                             @Field("image") image: String): AdminSignup

    @FormUrlEncoded
    @POST("admin_login.php")
    suspend fun admin_login (@Field("username") username: String,
                             @Field("password") password: String): AdminLogin

    @FormUrlEncoded
    @POST("get_admin.php")
    suspend fun get_admin (@Field("username") username: String): GetAdmin


    @FormUrlEncoded
    @POST("employee_signup.php")
    suspend fun employee_signup(@Field("firstname") firstname: String,
                             @Field("lastname") lastname: String,
                             @Field("gender") gender: String,
                             @Field("dob") dob: String,
                             @Field("address") address: String,
                             @Field("country") country: String,
                             @Field("designation") designation: String,
                             @Field("image")image: String): EmployeeSignup

    @GET("get_employee.php")
    suspend fun get_employee() : GetEmployee


}