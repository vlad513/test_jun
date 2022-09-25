package com.example.testprojeckt.ViewModels

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.testprojeckt.Server.AccesToken
import com.example.testprojeckt.Server.DataUser
import com.example.testprojeckt.Server.GitHub
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelMainScreen : ViewModel() {

    var asd: String = "emty"
    private val _usersInfo =
        MutableStateFlow<List<DataUser>>(listOf())
    val userInfo = _usersInfo.asStateFlow()

    fun GetGitTocken(code: String, clientId: String, clientSecret: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val client: GitHub = retrofit.create(GitHub::class.java)

        val accessTokens: Call<AccesToken> =
            client.getAccessToken(clientId, clientSecret, code)

        accessTokens.enqueue(object :
            Callback<AccesToken> {
            override fun onResponse(
                call: Call<AccesToken>,
                response: Response<AccesToken>?
            ) {
                asd = response?.body()?.accessToken.orEmpty()
                configureRetrofitq()
            }

            override fun onFailure(call: Call<AccesToken>, t: Throwable?) {

            }
        })
    }

    @SuppressLint("CheckResult")
    fun configureRetrofitq() {
        if (asd == "emty") {

        } else {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            val testApi: GitHub = retrofit.create(GitHub::class.java)

            testApi.getTestList("token $asd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _usersInfo.value = it

                }, {
            })
        }
    }
}

