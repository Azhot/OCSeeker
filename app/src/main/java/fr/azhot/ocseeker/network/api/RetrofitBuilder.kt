package fr.azhot.ocseeker.network.api

import fr.azhot.ocseeker.util.OCS_API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "$OCS_API_URL/apps/v2/"

    private val retrofitService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofitService.create(serviceClass)
    }
}