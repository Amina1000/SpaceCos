package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

//Donki -Coronal Mass Ejection (CME) Analysis
data class DonkiCmeResponseData(

    @SerializedName("activityID")
    val activityId: String?,

    @SerializedName("note")
    val note: String?,

    @SerializedName("link")
    val link: String?,

    @SerializedName("startTime")
    val startTime: String?
)