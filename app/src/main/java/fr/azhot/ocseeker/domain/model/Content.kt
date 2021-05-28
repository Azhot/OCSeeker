package fr.azhot.ocseeker.domain.model

data class Content(
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val fullScreenImageUrl: String,
    val detailLink: String,
    var pitch: String? = null,
)