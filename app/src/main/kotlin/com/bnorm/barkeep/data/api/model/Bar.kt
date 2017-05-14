package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Bar(var name: String? = null,
               var ingredients: List<Ingredient> = ArrayList()) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Bar(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.readString(),
                    parcel.createTypedArrayList(Ingredient.CREATOR))

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeTypedList(ingredients)
    }
}
