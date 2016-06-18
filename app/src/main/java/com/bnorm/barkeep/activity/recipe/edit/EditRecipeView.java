package com.bnorm.barkeep.activity.recipe.edit;

import java.util.List;

import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Recipe;

public interface EditRecipeView {
    String RECIPE_TAG = EditRecipeView.class.getName() + ".recipe";

    void onClose();

    void onRecipeSaved(Recipe recipe);

    void onComponentDialog(Integer position, Component component, String negativeText);

    // todo extract recipe some other way?
    String getName();

    String getDescription();

    String getDirections();

    List<Component> getComponents();
}
