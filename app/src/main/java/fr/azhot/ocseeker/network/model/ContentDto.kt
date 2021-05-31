package fr.azhot.ocseeker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentDto(
    @SerializedName("title")
    @Expose
    val title: List<TitleDto>? = null,
    @SerializedName("subtitle")
    @Expose
    val subtitle: String? = null,
    @SerializedName("subtitlefocus")
    @Expose
    val subtitlefocus: List<String>? = null,
    @SerializedName("highlefticon")
    @Expose
    val highlefticon: List<String>? = null,
    @SerializedName("highrighticon")
    @Expose
    val highrighticon: List<String>? = null,
    @SerializedName("lowrightinfo")
    @Expose
    val lowrightinfo: List<String>? = null,
    @SerializedName("imageurl")
    @Expose
    val imageurl: String? = null,
    @SerializedName("fullscreenimageurl")
    @Expose
    val fullscreenimageurl: String? = null,
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("detaillink")
    @Expose
    val detaillink: String? = null,
    @SerializedName("duration")
    @Expose
    val duration: String? = null,
    @SerializedName("playinfoid")
    @Expose
    val playinfoid: PlayinfoidDto? = null,
)