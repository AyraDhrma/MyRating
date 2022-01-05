package id.co.cpm.myrating.datasource


import com.google.gson.annotations.SerializedName

data class DataVideo(
    @SerializedName("INF_Aktif")
    val iNFAktif: String,
    @SerializedName("INF_Durasi")
    val iNFDurasi: String,
    @SerializedName("INF_ID")
    val iNFID: String,
    @SerializedName("INF_Keterangan")
    val iNFKeterangan: String,
    @SerializedName("INF_Nama")
    val iNFNama: String,
    @SerializedName("INF_Tipe")
    val iNFTipe: String,
    @SerializedName("INF_Urutan")
    val iNFUrutan: String,
    @SerializedName("INF_Volume")
    val iNFVolume: String
)