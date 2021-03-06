package fr.azhot.ocseeker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TitleDto(
    @SerializedName("color")
    @Expose
    val color: String? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("value")
    @Expose
    val value: String? = null,
)