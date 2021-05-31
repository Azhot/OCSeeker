package fr.azhot.ocseeker.network.util

import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.model.ContentDto

object ContentDtoMapper {

    fun mapToDomain(model: ContentDto): Content = Content(
        title = model.title?.get(0)?.value,
        subtitle = model.subtitle,
        imageUrl = model.imageurl,
        fullScreenImageUrl = model.fullscreenimageurl,
        detailLink = model.detaillink,
    )
}