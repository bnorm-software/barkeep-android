package com.bnorm.barkeep.server.data.store;

import java.util.ArrayList;
import java.util.List;

import com.bnorm.barkeep.server.data.store.v1.Component;
import com.bnorm.barkeep.server.data.store.v1.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.Recipe;
import com.google.appengine.repackaged.com.google.common.collect.Sets;

public final class Recipes {

    /** Private constructor for utility class. */
    private Recipes() {
    }

    public static RecipeWriter write(String name) {
        return new RecipeWriter(name);
    }

    public static class RecipeWriter {

        private final String name;
        private String description;
        private List<Component> components;
        private String directions;

        public RecipeWriter(String name) {
            this.name = name;
            this.description = null;
            this.components = new ArrayList<>();
            this.directions = null;
        }

        public RecipeWriter description(String description) {
            this.description = description;
            return this;
        }

        public RecipeWriter add(Measure.ComponentBuilder<?> builder) {
            Component component = new Component();
            component.setIngredients(builder.getIngredients());
            component.setAmount(builder.getAmount());
            component.setUnit(builder.getUnit().toString());
            this.components.add(component);
            return this;
        }

        public RecipeWriter directions(String recipe) {
            this.directions = recipe;
            return this;
        }

        public Recipe author() {
            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setNameWords(Sets.newHashSet(name.toLowerCase().split(" ")));
            recipe.setDescription(description);
            recipe.setComponents(components);
            recipe.setDirections(directions);
            return recipe;
        }

        public Recipe author(Endpoint endpoint) {
            Recipe recipe = author();
            endpoint.insertRecipe(recipe);
            return recipe;
        }
    }
}
