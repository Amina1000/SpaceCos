package com.cocos.develop.spacecos.data.datasource

import com.cocos.develop.spacecos.data.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * homework com.cocos.develop.spacecos.data.datasource
 *
 * @author Amina
 * 27.08.2021
 */
interface NasaApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>
}