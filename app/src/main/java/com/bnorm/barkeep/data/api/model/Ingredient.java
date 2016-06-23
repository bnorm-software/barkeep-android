package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private String name;
    private List<String> nameWords;
    private UnitType type;

    public Ingredient() {
    }

    public Ingredient(com.bnorm.barkeep.server.data.store.v1.endpoint.model.Ingredient ingredient) {
        this.name = ingredient.getName();
        this.nameWords = ingredient.getNameWords();
        this.type = UnitType.valueOf(ingredient.getType());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNameWords() {
        return nameWords;
    }

    public void setNameWords(List<String> nameWords) {
        this.nameWords = nameWords;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public com.bnorm.barkeep.server.data.store.v1.endpoint.model.Ingredient toStore() {
        com.bnorm.barkeep.server.data.store.v1.endpoint.model.Ingredient ingredient;
        ingredient = new com.bnorm.barkeep.server.data.store.v1.endpoint.model.Ingredient();
        ingredient.setName(name);
        ingredient.setNameWords(new ArrayList<>(nameWords));
        ingredient.setType(type.toString());
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.nameWords);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    protected Ingredient(Parcel in) {
        this.name = in.readString();
        this.nameWords = in.createStringArrayList();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : UnitType.values()[tmpType];
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
