package com.bnorm.barkeep.data.api.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Recipe(var name: String? = null,
             var nameWords: List<String>? = null,
             var picture: String? = null,
             var description: String? = null,
             var components: List<Component> = ArrayList(),
             var directions: String? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR = createParcel { Recipe(it) }
    }

    protected constructor(parcel: Parcel) :
            this(parcel.readString(),
                    parcel.createStringArrayList(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.createTypedArrayList(Component.CREATOR),
                    parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
        dest.writeStringList(this.nameWords)
        dest.writeString(this.picture)
        dest.writeString(this.description)
        dest.writeTypedList(components)
        dest.writeString(this.directions)
    }
}
