package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

/****
Project Nasa Photo Day
Package softing.ubah4ukdev.nasaphotoday.network.responses

Created by Ivan Sheynmaer

2021.07.11
v1.0
 */
//EPIC - Earth Polychromatic Imaging Camera
data class EpicResponseData(
    @SerializedName("identifier")
    val identifier: String?,

    @SerializedName("caption")
    val caption: String?,

    @SerializedName("version")
    val version: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("date")
    val date: String?
)