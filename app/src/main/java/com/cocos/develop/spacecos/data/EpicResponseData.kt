package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName


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
    val date: String?,

    var pathPicture: String?
)