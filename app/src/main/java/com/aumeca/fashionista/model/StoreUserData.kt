package com.aumeca.fashionista.model

import android.os.Parcel
import android.os.Parcelable

data class StoreUserData(
    var u_id: String = "",
    var task:ArrayList<StorageClass> = ArrayList()
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(StorageClass.CREATOR)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(u_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreUserData> {
        override fun createFromParcel(parcel: Parcel): StoreUserData {
            return StoreUserData(parcel)
        }

        override fun newArray(size: Int): Array<StoreUserData?> {
            return arrayOfNulls(size)
        }
    }
}