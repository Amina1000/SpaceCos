package com.cocos.develop.spacecos.data.datasource

import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.data.PodServerResponseData
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
    //Фото дня
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PodServerResponseData>

    //Фото Марса
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPicture(
        @Query("earth_date") date: String,
        @Query("api_key") apiKey: String): Call<MarsResponseData>

    //Фото Земли
    @GET("EPIC/api/natural")
    fun getEarthPicture(@Query("api_key")apiKey: String): Call<ArrayList<EpicResponseData>>

    // Погода CME
    @GET("DONKI/CME")
    fun getDonkiCme(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("api_key") apiKey: String): Call<ArrayList<DonkiCmeResponseData>>
}