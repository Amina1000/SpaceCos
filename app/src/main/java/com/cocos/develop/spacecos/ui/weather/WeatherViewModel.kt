package com.cocos.develop.spacecos.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.DonkiCmeResponseData
import com.cocos.develop.spacecos.data.repository.RepositoryImplementation
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.domain.Repository
import com.cocos.develop.spacecos.utils.resources
import com.cocos.develop.spacecos.utils.startDay
import com.cocos.develop.spacecos.utils.yesterday
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val mainRepository: Repository by lazy { RepositoryImplementation() }
    private val liveDataToObserveAppStates: MutableLiveData<AppStates> = MutableLiveData()

    fun getEarthPicture(): LiveData<AppStates> {
        sendServerRequest()
        return liveDataToObserveAppStates
    }

    private fun sendServerRequest() {
        liveDataToObserveAppStates.value = AppStates.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataToObserveAppStates.postValue(AppStates.Error(
                Throwable(resources.getString(R.string.api_key_error))
            ))
        } else {
            mainRepository.getWeatherCme(apiKey, startDay(), yesterday()).enqueue(object :
                Callback<ArrayList<DonkiCmeResponseData>> {
                override fun onResponse(
                    call: Call<ArrayList<DonkiCmeResponseData>>,
                    response: Response<ArrayList<DonkiCmeResponseData>>
                ) {
                    val serverResponse: ArrayList<DonkiCmeResponseData>? = response.body()
                    if (response.isSuccessful && serverResponse != null) {
                        liveDataToObserveAppStates.postValue(
                            AppStates.Success(serverResponse)
                        )
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserveAppStates.postValue(
                                AppStates.Error(Throwable(resources.getString(R.string.unidentified_error)))
                            )
                        } else {
                            liveDataToObserveAppStates.postValue(AppStates.Error(Throwable(message)))
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<DonkiCmeResponseData>>, t: Throwable) {
                    liveDataToObserveAppStates.postValue(AppStates.Error(t))
                }
            })
        }
    }
}