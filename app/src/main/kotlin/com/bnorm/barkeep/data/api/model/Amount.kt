package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Amount(var recommended: Double? = null,
                  var min: Double? = null,
                  var max: Double? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Amount(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.readDouble(),
                    parcel.readDouble(),
                    parcel.readDouble())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(recommended)
        dest.writeValue(min)
        dest.writeValue(max)
    }
}
