package com.up.employee.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.up.employee.data.remote.EmployeeApi
import com.up.employee.data.remote.PrintfulApi
import com.up.employee.util.Url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    @EmployeeQualifier
    fun provideEmployeeRetrofit(gson: Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Url.BASE_URL)

    }

    @Singleton
    @Provides
    @PrintfulQualifier
    fun providePrintfulRetrofit(gson: Gson): Retrofit.Builder{
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.printful.com/")

    }

    @Singleton
    @Provides
    fun provideEmployeeApi(@EmployeeQualifier retrofit: Retrofit.Builder): EmployeeApi{
        return retrofit
            .build()
            .create(EmployeeApi::class.java)
    }

    @Singleton
    @Provides
    fun providePrintfulApi(@PrintfulQualifier retrofit: Retrofit.Builder): PrintfulApi {
        return retrofit
            .build()
            .create(PrintfulApi::class.java)
    }

}