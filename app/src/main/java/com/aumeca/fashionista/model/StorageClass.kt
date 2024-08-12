package com.aumeca.fashionista.model

import android.os.Parcel
import android.os.Parcelable

data class StorageClass (
    var id: String = "",
    var date: String = "",
    var time: String = "",
    var plan: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(plan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StorageClass> {
        override fun createFromParcel(parcel: Parcel): StorageClass {
            return StorageClass(parcel)
        }

        override fun newArray(size: Int): Array<StorageClass?> {
            return arrayOfNulls(size)
        }
    }
}