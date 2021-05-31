package fr.azhot.ocseeker.repository

import fr.azhot.ocseeker.domain.model.Content

interface ContentRepository {

    suspend fun getContents(title: String): List<Content>?
}

