package com.bnorm.barkeep.activity.recipe.search;

import java.util.List;

import com.bnorm.barkeep.server.data.store.Recipe;

public interface SearchRecipeView {

    void onRecipeView(Recipe recipe);

    void onSearchResults(List<Recipe> results);
}
