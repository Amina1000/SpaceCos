package com.cocos.develop.spacecos.domain


/**
 * homework com.cocos.develop.spacecos.domain
 *
 * @author Amina
 * 27.08.2021
 */
sealed class AppStates {
    data class Success<T>(val serverResponseData: T) : AppStates()
    data class Error(val error: Throwable) : AppStates()
    data class Loading(val progress: Int?) : AppStates()
}