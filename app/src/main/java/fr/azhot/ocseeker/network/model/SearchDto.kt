package fr.azhot.ocseeker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("template")
    @Expose
    val template: String? = null,
    @SerializedName("parentalrating")
    @Expose
    val parentalrating: Int? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("offset")
    @Expose
    val offset: Int? = null,
    @SerializedName("limit")
    @Expose
    val limit: String? = null,
    @SerializedName("next")
    @Expose
    val next: String? = null,
    @SerializedName("previous")
    @Expose
    val previous: String? = null,
    @SerializedName("total")
    @Expose
    val total: Int? = null,
    @SerializedName("count")
    @Expose
    val count: Int? = null,
    @SerializedName("filter")
    @Expose
    val filter: String? = null,
    @SerializedName("sort")
    @Expose
    val sort: String? = null,
    @SerializedName("contents")
    @Expose
    val contents: List<ContentDto>? = null
)