package fr.azhot.ocseeker.domain.model

data class Content(
    val title: String? = null,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val fullScreenImageUrl: String? = null,
    val detailLink: String? = null,
    var pitch: String? = null,
)