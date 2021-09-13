package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

data class MarsResponseData(
    @SerializedName("photos")
    val photoList: List<MarsEntity>?
)