package com.example.mvvmsample.data.network

import com.example.mvvmsample.data.network.responses.AuthResponse
import com.example.mvvmsample.data.network.responses.QuotesResponse
import com.example.mvvmsample.util.JsonContance
import com.example.mvvmsample.util.ServerUtils
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST(ServerUtils.API_LOGIN)
    suspend fun userLogin(
        @Field(JsonContance.email) email: String,
        @Field(JsonContance.password) password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST(ServerUtils.API_SIGNUP)
    suspend fun userSignUp(
        @Field(JsonContance.name) name: String,
        @Field(JsonContance.email) email: String,
        @Field(JsonContance.password) password: String
    ): Response<AuthResponse>

    @POST(ServerUtils.API_QUOTES)
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {

            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl(ServerUtils.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}