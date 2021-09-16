package com.cocos.develop.spacecos.domain

import com.cocos.develop.spacecos.data.PodServerResponseData

/**
 * homework com.cocos.develop.spacecos.domain
 *
 * @author Amina
 * 27.08.2021
 */
sealed class AppStates {
    data class Success(val serverResponseData: PodServerResponseData) : AppStates()
    data class Error(val error: Throwable) : AppStates()
    data class Loading(val progress: Int?) : AppStates()
}