package fr.azhot.ocseeker.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Playinfoid(
    @SerializedName("hd")
    @Expose
    val hd: String? = null,
    @SerializedName("sd")
    @Expose
    val sd: String? = null,
    @SerializedName("uhd")
    @Expose
    val uhd: String? = null,
)