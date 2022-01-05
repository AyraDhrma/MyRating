package id.co.cpm.myrating.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService(private val url: String) {

    val TIMEOUT = 150

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    private val okHttpClientInstance: OkHttpClient by lazy {
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)
        client.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.build()
    }

    val RETROFIT_INSTANCE: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClientInstance)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}
