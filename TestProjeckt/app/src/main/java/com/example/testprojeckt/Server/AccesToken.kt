package com.example.testprojeckt.Server

import com.google.gson.annotations.SerializedName

data class AccesToken(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String
)
