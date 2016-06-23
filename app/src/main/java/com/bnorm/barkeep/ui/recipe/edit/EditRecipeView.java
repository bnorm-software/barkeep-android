package com.bnorm.barkeep.ui.recipe.edit;

import java.util.List;

import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;

public interface EditRecipeView {

    void onClose();

    void onRecipeSaved(Recipe recipe);

    void onComponentDialog(Integer position, Component component, String negativeText);

    // todo extract recipe some other way?
    String getName();

    String getDescription();

    String getDirections();

    List<Component> getComponents();
}
