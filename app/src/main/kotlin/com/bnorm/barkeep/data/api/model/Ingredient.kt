package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable

data class Ingredient(var name: String? = null,
                 var nameWords: List<String>? = null,
                 var type: UnitType? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Ingredient(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.readString(),
                    parcel.createStringArrayList(),
                    parcel.readInt().let { if (it == -1) null else UnitType.values()[it] })

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
        dest.writeStringList(this.nameWords)
        dest.writeInt(if (this.type == null) -1 else this.type!!.ordinal)
    }
}
