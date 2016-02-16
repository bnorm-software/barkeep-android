package com.bnorm.barkeep.server;

import com.bnorm.barkeep.server.data.store.v1.Amount;
import com.bnorm.barkeep.server.data.store.v1.Component;
import com.bnorm.barkeep.server.data.store.v1.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.Ingredient;
import com.bnorm.barkeep.server.data.store.v1.Recipe;
import com.bnorm.barkeep.server.wrapper.ApiWrapper;
import com.bnorm.barkeep.server.wrapper.LocalWrapper;
import com.google.api.server.spi.response.CollectionResponse;

public class RetrieveData {
    public static void main(String[] args) throws Throwable {
        ApiWrapper wrapper = new LocalWrapper();
        wrapper.initialize();
        try {
            run();
        } finally {
            wrapper.terminate();
        }
    }


    public static void run() throws Throwable {
        Endpoint endpoint = new Endpoint();

        CollectionResponse<Recipe> recipes = endpoint.listRecipes(null, null, null, null);
        for (Recipe recipe : recipes.getItems()) {
            System.out.println(recipe.getName());
            if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
                System.out.println("   Description : " + recipe.getDescription());
            }
            if (recipe.getDirections() != null && !recipe.getDirections().isEmpty()) {
                System.out.println("   Directions : " + recipe.getDirections());
            }
            for (Component component : recipe.getComponents()) {
                StringBuilder builder = new StringBuilder("   ");
                boolean first = true;
                for (Ingredient ingredient : component.getIngredients()) {
                    if (!first) {
                        builder.append(" or ");
                    }
                    builder.append(ingredient.getName());
                    first = false;
                }
                builder.append(" : ");

                Amount amount = component.getAmount();
                if (amount.getRecommended() != null) {
                    builder.append(amount.getRecommended());
                } else {
                    builder.append(amount.getMin()).append(" to ").append(amount.getMax());
                }

                builder.append(" ").append(component.getUnit());
                System.out.println(builder);
            }
        }
    }
}
