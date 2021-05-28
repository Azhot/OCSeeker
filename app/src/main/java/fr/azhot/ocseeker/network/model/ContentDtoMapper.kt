package fr.azhot.ocseeker.network.model

import fr.azhot.ocseeker.domain.model.Content

object ContentDtoMapper {
    fun mapToDomain(model: ContentDto): Content {
        return Content(
            title = model.title[0].value,
            subtitle = model.subtitle,
            imageUrl = model.imageurl,
            fullScreenImageUrl = model.fullscreenimageurl,
            detailLink = model.detaillink,
        )
    }
}