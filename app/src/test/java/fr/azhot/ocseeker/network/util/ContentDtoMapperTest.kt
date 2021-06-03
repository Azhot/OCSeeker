package fr.azhot.ocseeker.network.util

import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.model.ContentDto
import fr.azhot.ocseeker.network.model.TitleDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ContentDtoMapperTest {

    @Test
    fun canMapToDomain() {

        val contentDto = ContentDto(
            title = listOf(TitleDto(value = "title")),
            subtitle = "subtitle",
            imageurl = "imageurl",
            fullscreenimageurl = "fullscreenimageurl",
            detaillink = "detaillink",
        )
        val contentMapped = ContentDtoMapper.mapToDomain(contentDto)
        val contentExpected = Content(
            "title",
            "subtitle",
            "imageurl",
            "fullscreenimageurl",
            "detaillink",
        )

        assertEquals(contentExpected, contentMapped)
    }
}