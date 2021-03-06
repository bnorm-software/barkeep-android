package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Component implements Parcelable {

    private List<Ingredient> ingredients = new ArrayList<>();
    private Amount amount;
    private String unit;

    public Component() {
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Amount getAmount() {
        if (amount == null) {
            amount = new Amount();
        }
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ingredients);
        dest.writeParcelable(this.amount, 0);
        dest.writeString(this.unit);
    }

    protected Component(Parcel in) {
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.amount = in.readParcelable(Amount.class.getClassLoader());
        this.unit = in.readString();
    }

    public static final Parcelable.Creator<Component> CREATOR = new Parcelable.Creator<Component>() {
        public Component createFromParcel(Parcel source) {
            return new Component(source);
        }

        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
}
