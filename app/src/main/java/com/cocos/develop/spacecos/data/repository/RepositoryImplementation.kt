package com.cocos.develop.spacecos.data.repository

import com.cocos.develop.spacecos.data.PodServerResponseData
import com.cocos.develop.spacecos.data.datasource.PodRetrofit
import com.cocos.develop.spacecos.domain.Repository
import retrofit2.Call

/**
 * homework com.cocos.develop.dictionarykiss.data.repository
 *
 * @author Amina
 * 26.08.2021
 */
class RepositoryImplementation : Repository {

    private val retrofit = PodRetrofit()

    override fun getPictureOfTheDay(apiKey: String): Call<PodServerResponseData> {
        return retrofit.getPictureOfTheDay(apiKey)
    }
}