package com.cocos.develop.spacecos.ui.earth

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cocos.develop.spacecos.BuildConfig
import com.cocos.develop.spacecos.R
import com.cocos.develop.spacecos.data.EpicResponseData
import com.cocos.develop.spacecos.data.repository.RepositoryImplementation
import com.cocos.develop.spacecos.domain.AppStates
import com.cocos.develop.spacecos.domain.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val EPIC_PIC_URL =  "https://api.nasa.gov/EPIC/archive/natural/"

class EarthViewModel : ViewModel() {

    private val mainRepository: Repository by lazy { RepositoryImplementation() }
    private val liveDataToObserveAppStates: MutableLiveData<AppStates> = MutableLiveData()
    private val resources = Resources.getSystem()

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
            mainRepository.getEarthPicture(apiKey).enqueue(object :
                Callback<ArrayList<EpicResponseData>> {
                override fun onResponse(
                    call: Call<ArrayList<EpicResponseData>>,
                    response: Response<ArrayList<EpicResponseData>>
                ) {
                    val serverResponse: ArrayList<EpicResponseData>? = response.body()
                    if (response.isSuccessful && serverResponse != null) {
                        checkResponse(serverResponse)
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

                override fun onFailure(call: Call<ArrayList<EpicResponseData>>, t: Throwable) {
                    liveDataToObserveAppStates.postValue(AppStates.Error(t))
                }
            })
        }
    }

    private fun checkResponse(serverResponse: ArrayList<EpicResponseData>?) {

        serverResponse?.forEach {
            var dateRequest = ""
            it.date?.let { date ->
                dateRequest =
                    date.substring(0, 10).replace("-", "/")
            }
            it.pathPicture =
                EPIC_PIC_URL + dateRequest + "/png/${it.image}.png?api_key=${BuildConfig.NASA_API_KEY}"
        }
    }
}