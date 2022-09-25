package com.example.testprojeckt.Server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataUser(
    @SerializedName("login")
    val login: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("avatar")
    val avatar: String,
//
//    @SerializedName("created_at")
//    val created_at: String,
//
//    @SerializedName("company")
//    val comany: String,
//
//    @SerializedName("location")
//    val location: String,
//
//    @SerializedName("email")
//    val email: String
) : Parcelable
