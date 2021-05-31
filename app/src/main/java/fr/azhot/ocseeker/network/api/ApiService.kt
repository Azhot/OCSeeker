package fr.azhot.ocseeker.network.api

import fr.azhot.ocseeker.network.model.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("contents?")
    suspend fun getSearch(@Query("search") search: String): SearchDto
}