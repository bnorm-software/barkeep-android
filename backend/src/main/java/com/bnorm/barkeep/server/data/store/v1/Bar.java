package com.bnorm.barkeep.server.data.store.v1;

import java.util.ArrayList;
import java.util.List;

import com.bnorm.barkeep.server.data.store.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Bar {

    @Id private String name;
    private List<Ref<Ingredient>> ingredients = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return Deref.deref(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = Deref.ref(ingredients);
    }
}
