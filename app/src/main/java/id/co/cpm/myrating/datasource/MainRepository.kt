package id.co.cpm.myrating.datasource

import id.co.cpm.myrating.network.RetrofitService

class MainRepository(private val url: String) {
    suspend fun getInformation(requestModel: RequestModel) =
        RetrofitService(url).RETROFIT_INSTANCE.getInformation(requestModel)

    suspend fun saveRating(requestModel: RequestModel) =
        RetrofitService(url).RETROFIT_INSTANCE.saveRating(requestModel)
}