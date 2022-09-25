package com.example.testprojeckt.Server

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface GitHub {

    @Headers("Accept:application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String?
    ): Call<AccesToken>

    @GET("./users")
    @Headers("Content-Type:application/json", "Accept:application/json")
    fun getTestList(@Header("Authorization") token: String): Single<List<DataUser>>
}