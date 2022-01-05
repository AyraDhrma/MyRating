package id.co.cpm.myrating.datasource

import com.google.gson.annotations.SerializedName

data class RequestModel(
    @SerializedName("X-API-KEY") val apiKey: String,
    @SerializedName("data") val data: String
)