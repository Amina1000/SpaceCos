package com.cocos.develop.spacecos.data

import com.google.gson.annotations.SerializedName

/**
 * homework com.cocos.develop.spacecos.data
 *
 * @author Amina
 * 27.08.2021
 */
data class PodServerResponseData(
    @field:SerializedName("copyright") val copyright: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("hdurl") val hdUrl: String?
)
