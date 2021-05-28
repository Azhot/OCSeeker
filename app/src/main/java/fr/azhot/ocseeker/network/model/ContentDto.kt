package fr.azhot.ocseeker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentDto(
    @SerializedName("title")
    @Expose
    val title: List<Title>,
    @SerializedName("subtitle")
    @Expose
    val subtitle: String,
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
    val imageurl: String,
    @SerializedName("fullscreenimageurl")
    @Expose
    val fullscreenimageurl: String,
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("detaillink")
    @Expose
    val detaillink: String,
    @SerializedName("duration")
    @Expose
    val duration: String,
    @SerializedName("playinfoid")
    @Expose
    val playinfoid: Playinfoid,
)