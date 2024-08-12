package com.aumeca.fashionista.model

import android.os.Parcel
import android.os.Parcelable

data class Goal (
    var goal: String = "",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
          ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Goal> {
        override fun createFromParcel(parcel: Parcel): Goal {
            return Goal(parcel)
        }

        override fun newArray(size: Int): Array<Goal?> {
            return arrayOfNulls(size)
        }
    }
}
