package id.co.cpm.myrating.datasource


import com.google.gson.annotations.SerializedName

data class InformationModel(
    @SerializedName("data_running")
    val dataRunning: ArrayList<DataRunning>,
    @SerializedName("data_video")
    val dataVideo: ArrayList<DataVideo>,
    @SerializedName("message")
    val message: String,
    @SerializedName("rc")
    val rc: String,
    @SerializedName("response")
    val response: String
)