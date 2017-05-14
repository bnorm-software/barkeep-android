package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Component(var ingredients: List<Ingredient> = ArrayList(),
                     var amount: Amount? = Amount(),
                     var unit: String? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Component(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.createTypedArrayList(Ingredient.CREATOR),
                    parcel.readParcelable<Amount>(Amount::class.java.classLoader),
                    parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(this.ingredients)
        dest.writeParcelable(this.amount, 0)
        dest.writeString(this.unit)
    }
}
