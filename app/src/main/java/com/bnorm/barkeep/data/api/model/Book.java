package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String name;
    private List<Recipe> recipes = new ArrayList<>();

    public Book() {
    }

    public Book(com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book book) {
        this.name = book.getName();
        if (book.getRecipes() != null) {
            for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe recipe : book.getRecipes()) {
                this.recipes.add(new Recipe(recipe));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book toStore() {
        com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book book;
        book = new com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book();
        book.setName(name);
        for (Recipe recipe : recipes) {
            book.getRecipes().add(recipe.toStore());
        }
        return book;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.recipes);
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.recipes = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
