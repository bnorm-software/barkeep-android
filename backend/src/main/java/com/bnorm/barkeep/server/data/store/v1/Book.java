package com.bnorm.barkeep.server.data.store.v1;

import java.util.ArrayList;
import java.util.List;

import com.bnorm.barkeep.server.data.store.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Book {

    @Id private String name;
    private List<Ref<Recipe>> recipes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return Deref.deref(recipes);
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = Deref.ref(recipes);
    }
}
