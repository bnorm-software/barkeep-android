package com.bnorm.barkeep.server.data.store.v1;

import java.util.ArrayList;
import java.util.List;

import com.bnorm.barkeep.server.data.store.Deref;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

public class Component {

    @Index @Load private List<Ref<Ingredient>> ingredients = new ArrayList<>();
    private Amount amount;
    private String unit;

    public List<Ingredient> getIngredients() {
        return Deref.deref(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = Deref.ref(ingredients);
    }

    public Amount getAmount() {
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
}
