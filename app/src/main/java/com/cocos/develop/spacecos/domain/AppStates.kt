package com.cocos.develop.spacecos.domain

import com.cocos.develop.spacecos.data.PODServerResponseData

/**
 * homework com.cocos.develop.spacecos.domain
 *
 * @author Amina
 * 27.08.2021
 */
sealed class AppStates {
    data class Success(val serverResponseData: PODServerResponseData) : AppStates()
    data class Error(val error: Throwable) : AppStates()
    data class Loading(val progress: Int?) : AppStates()
}