package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

/****
Project Nasa Photo Day
Package softing.ubah4ukdev.nasaphotoday.network.responses

Created by Ivan Sheynmaer

2021.07.12
v1.0
 */
data class MarsResponseData(
    @SerializedName("photos")
    val photoList: List<MarsEntity>?
)