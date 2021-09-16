package com.cocos.develop.spacecos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.data.PodServerResponseData
import com.cocos.develop.spacecos.data.datasource.PodRetrofit
import com.cocos.develop.spacecos.domain.AppStates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserveAppStates: MutableLiveData<AppStates> = MutableLiveData(),
    private val retrofit: PodRetrofit = PodRetrofit()
    ) :
    ViewModel() {

        fun getData(): LiveData<AppStates> {
            sendServerRequest()
            return liveDataToObserveAppStates
        }

        private fun sendServerRequest() {
            liveDataToObserveAppStates.value = AppStates.Loading(null)
            val apiKey: String = BuildConfig.NASA_API_KEY
            if (apiKey.isBlank()) {
                liveDataToObserveAppStates.postValue(AppStates.Error(Throwable("You need API key")))
            } else {
                retrofit.getPictureOfTheDay(apiKey).enqueue(object :
                    Callback<PodServerResponseData> {
                    override fun onResponse(
                        call: Call<PodServerResponseData>,
                        response: Response<PodServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataToObserveAppStates.postValue(
                                AppStates.Success(response.body()!!))
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

                    override fun onFailure(call: Call<PodServerResponseData>, t: Throwable) {
                        liveDataToObserveAppStates.postValue(AppStates.Error(t))
                    }
                })
            }
        }
}