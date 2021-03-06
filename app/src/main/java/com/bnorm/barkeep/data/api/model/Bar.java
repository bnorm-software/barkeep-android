package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Bar implements Parcelable {

    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Bar() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(ingredients);
    }

    protected Bar(Parcel in) {
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Parcelable.Creator<Bar> CREATOR = new Parcelable.Creator<Bar>() {
        public Bar createFromParcel(Parcel source) {
            return new Bar(source);
        }

        public Bar[] newArray(int size) {
            return new Bar[size];
        }
    };
}
