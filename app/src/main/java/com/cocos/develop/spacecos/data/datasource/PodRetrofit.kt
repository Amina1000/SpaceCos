package com.cocos.develop.spacecos.data.datasource

import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.data.PodServerResponseData
import com.cocos.develop.spacecos.domain.Repository
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * homework com.cocos.develop.spacecos.data.datasource
 *
 * @author Amina
 * 27.08.2021
 */
class PodRetrofit {

    private val baseUrl = "https://api.nasa.gov/"

    private val nasaApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient(PodInterceptor()))
        .build()
        .create(NasaApi::class.java)

    fun getPictureOfTheDay(apiKey: String): Call<PodServerResponseData> {
       return nasaApi.getPictureOfTheDay(apiKey)
    }

    fun getMarsPicture(apiKey: String, date: String): Call<MarsResponseData> {
        return nasaApi.getMarsPicture(apiKey, date)
    }

    fun getEarthPicture(apiKey: String): Call<ArrayList<EpicResponseData>>{
        return nasaApi.getEarthPicture(apiKey)
    }
    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class PodInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}