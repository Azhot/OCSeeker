package fr.azhot.ocseeker.repository

import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.api.ApiService
import fr.azhot.ocseeker.network.util.ContentDtoMapper

class ContentRepositoryImpl(private val apiService: ApiService) : ContentRepository {

    override suspend fun getContents(title: String): List<Content>? = apiService
        .getSearch(search = "title=$title")
        .contents
        ?.map { contentDto ->
            ContentDtoMapper.mapToDomain(contentDto)
        }
}