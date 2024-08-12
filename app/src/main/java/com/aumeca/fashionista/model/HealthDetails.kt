package com.aumeca.fashionista.model

import android.os.Parcel
import android.os.Parcelable

data class HealthDetails (
    var gender: String = "",
    var age: String = "",
    var weight: String = "",
    var height: String = "",
    var workoutType: String = "",
    var duration: String = "",
    var disease: String = "",
    var isDone: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(gender)
        parcel.writeString(age)
        parcel.writeString(weight)
        parcel.writeString(height)
        parcel.writeString(workoutType)
        parcel.writeString(duration)
        parcel.writeString(disease)
        parcel.writeByte(if (isDone) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HealthDetails> {
        override fun createFromParcel(parcel: Parcel): HealthDetails {
            return HealthDetails(parcel)
        }

        override fun newArray(size: Int): Array<HealthDetails?> {
            return arrayOfNulls(size)
        }
    }
}