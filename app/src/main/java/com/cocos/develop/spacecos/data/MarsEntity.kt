package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

data class MarsEntity(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("sol")
    val sol: Int?,

    @SerializedName("img_src")
    val image: String?,

    @SerializedName("earth_date")
    val date: String?
)

