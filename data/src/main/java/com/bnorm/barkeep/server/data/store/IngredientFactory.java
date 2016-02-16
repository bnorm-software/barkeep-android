package com.bnorm.barkeep.server.data.store;

import com.bnorm.barkeep.measurement.UnitType;
import com.bnorm.barkeep.server.data.store.v1.Ingredient;
import com.bnorm.barkeep.server.data.store.v1.Endpoint;
import com.google.common.collect.Sets;

public class IngredientFactory {

    private final Endpoint endpoint;

    public IngredientFactory(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public IngredientFactory() {
        this(null);
    }

    public Ingredient create(String name, UnitType type) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setNameWords(Sets.newHashSet(name.toLowerCase().split(" ")));
        ingredient.setType(type);

        if (endpoint != null) {
            endpoint.insertIngredient(ingredient);
        }

        return ingredient;
    }
}
