package com.cocos.develop.spacecos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.data.PODServerResponseData
import com.cocos.develop.spacecos.data.datasource.PODRetrofitImpl
import com.cocos.develop.spacecos.domain.AppStates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataToObserveAppStates: MutableLiveData<AppStates> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
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
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                    Callback<PODServerResponseData> {
                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
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

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataToObserveAppStates.postValue(AppStates.Error(t))
                    }
                })
            }
        }
}