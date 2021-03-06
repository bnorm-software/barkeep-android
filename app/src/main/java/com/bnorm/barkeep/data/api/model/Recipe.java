package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private String name;
    private List<String> nameWords;
    private String picture;
    private String description;
    private List<Component> components = new ArrayList<>();
    private String directions;

    public Recipe() {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.nameWords);
        dest.writeString(this.picture);
        dest.writeString(this.description);
        dest.writeTypedList(components);
        dest.writeString(this.directions);
    }

    protected Recipe(Parcel in) {
        this.name = in.readString();
        this.nameWords = in.createStringArrayList();
        this.picture = in.readString();
        this.description = in.readString();
        this.components = in.createTypedArrayList(Component.CREATOR);
        this.directions = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
