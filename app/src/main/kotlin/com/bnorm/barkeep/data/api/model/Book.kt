package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Book(var name: String? = null,
                var recipes: List<Recipe> = ArrayList()) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Book(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.readString(),
                    parcel.createTypedArrayList(Recipe.CREATOR))

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeTypedList(recipes)
    }
}
