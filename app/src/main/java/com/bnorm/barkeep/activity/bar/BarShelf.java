package com.bnorm.barkeep.activity.bar;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import com.bnorm.barkeep.server.data.store.Ingredient;

public class BarShelf implements Parcelable {

    private final List<Ingredient> ingredients;

    public BarShelf(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(ingredients);
    }

    protected BarShelf(Parcel in) {
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Parcelable.Creator<BarShelf> CREATOR = new Parcelable.Creator<BarShelf>() {
        public BarShelf createFromParcel(Parcel source) {
            return new BarShelf(source);
        }

        public BarShelf[] newArray(int size) {
            return new BarShelf[size];
        }
    };
}
