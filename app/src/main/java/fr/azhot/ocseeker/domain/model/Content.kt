package fr.azhot.ocseeker.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Content(
    val title: String? = null,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val fullScreenImageUrl: String? = null,
    val detailLink: String? = null,
    var pitch: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeString(imageUrl)
        parcel.writeString(fullScreenImageUrl)
        parcel.writeString(detailLink)
        parcel.writeString(pitch)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }

        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }

}