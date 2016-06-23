package com.bnorm.barkeep.ui.recipe.search;

import java.util.List;

import com.bnorm.barkeep.data.api.model.Recipe;

public interface SearchRecipeView {

    void onRecipeView(Recipe recipe);

    void onSearchResults(List<Recipe> results);
}
