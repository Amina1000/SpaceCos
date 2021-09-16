package com.cocos.develop.spacecos.domain

import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.data.PodServerResponseData
import retrofit2.Call

/**
 * homework com.cocos.develop.dictionarykiss.domain
 *
 * @author Amina
 * 26.08.2021
 */
interface Repository {
    fun getPictureOfTheDay(apiKey: String): Call<PodServerResponseData>
    fun getMarsPicture(apiKey: String, date: String): Call<MarsResponseData>
    fun getEarthPicture(apiKey: String): Call<ArrayList<EpicResponseData>>
    fun getWeatherCme(apiKey: String, startDate: String, endDate: String)
            : Call<ArrayList<DonkiCmeResponseData>>
}