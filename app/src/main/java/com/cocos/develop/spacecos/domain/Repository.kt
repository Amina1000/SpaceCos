package com.cocos.develop.spacecos.domain
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
}