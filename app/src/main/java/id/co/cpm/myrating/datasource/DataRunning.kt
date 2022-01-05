package id.co.cpm.myrating.datasource


import com.google.gson.annotations.SerializedName

data class DataRunning(
    @SerializedName("rt_id")
    val rtId: String,
    @SerializedName("rt_teks")
    val rtTeks: String,
    @SerializedName("rt_tipe")
    val rtTipe: String
)