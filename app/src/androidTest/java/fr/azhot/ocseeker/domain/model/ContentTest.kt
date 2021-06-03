package fr.azhot.ocseeker.domain.model

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentTest {

    @Test
    fun contentIsParcelable() {

        val content = Content()
        val parcel = Parcel.obtain()
        content.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val createdFromParcel: Content = Content.CREATOR.createFromParcel(parcel)

        assertEquals(content, createdFromParcel)
    }
}