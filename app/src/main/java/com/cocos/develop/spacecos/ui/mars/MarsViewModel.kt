package com.cocos.develop.spacecos.ui.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.data.MarsResponseData
import com.cocos.develop.spacecos.data.repository.RepositoryImplementation
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.domain.Repository
import com.cocos.develop.spacecos.utils.yesterday
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel : ViewModel() {
    private val mainRepositoryImpl: Repository by lazy{ RepositoryImplementation() }
    private val liveDataToObserveAppStates: MutableLiveData<AppStates> = MutableLiveData()

    fun getMarsPicture(): LiveData<AppStates> {
        sendServerRequest()
        return liveDataToObserveAppStates
    }

    private fun sendServerRequest() {
        liveDataToObserveAppStates.value = AppStates.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataToObserveAppStates.postValue(AppStates.Error(Throwable("You need API key")))
        } else {
            mainRepositoryImpl.getMarsPicture(apiKey, yesterday()).enqueue(object :
                Callback<MarsResponseData> {
                override fun onResponse(
                    call: Call<MarsResponseData>,
                    response: Response<MarsResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserveAppStates.postValue(
                            AppStates.Success(response.body()!!)
                        )
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserveAppStates.postValue(
                                AppStates.Error(Throwable("Unidentified error"))
                            )
                        } else {
                            liveDataToObserveAppStates.postValue(AppStates.Error(Throwable(message)))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsResponseData>, t: Throwable) {
                    liveDataToObserveAppStates.postValue(AppStates.Error(t))
                }
            })
        }
    }
}