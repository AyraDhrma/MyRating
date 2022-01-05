package id.co.cpm.myrating.network

import id.co.cpm.myrating.datasource.InformationModel
import id.co.cpm.myrating.datasource.RequestModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/feedback_api/Feedback/video")
    suspend fun getInformation(@Body user: RequestModel): InformationModel

}