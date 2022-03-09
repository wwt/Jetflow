package com.wwt.jetflowlibrary.navigation

import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import androidx.compose.runtime.Immutable
import java.nio.ByteBuffer
import java.util.*

@Immutable
data class NavigationId(private val uuid: UUID = UUID.randomUUID()) : Parcelable {

    private constructor(parcel: Parcel) : this(UUID(parcel.readLong(), parcel.readLong()))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(uuid.mostSignificantBits)
        parcel.writeLong(uuid.leastSignificantBits)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NavigationId> {
        override fun createFromParcel(parcel: Parcel): NavigationId {
            return NavigationId(parcel)
        }

        override fun newArray(size: Int): Array<NavigationId?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        val byteBuffer = ByteBuffer.allocate(16)
        byteBuffer.putLong(uuid.mostSignificantBits)
        byteBuffer.putLong(uuid.leastSignificantBits)
        return Base64.encodeToString(byteBuffer.array(), Base64.NO_WRAP or Base64.NO_PADDING)
    }

}