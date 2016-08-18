package com.bnorm.barkeep.ui.recipe.edit;

import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;

public interface EditRecipeView {

    void onRecipeSaved(Recipe recipe);

    void onEditComponent(Integer position, Component component, String negativeText);
}
