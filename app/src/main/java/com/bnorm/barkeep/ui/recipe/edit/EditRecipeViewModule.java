package com.bnorm.barkeep.ui.recipe.edit;

import dagger.Module;
import dagger.Provides;

@Module
public class EditRecipeViewModule {

    private final EditRecipeView view;

    public EditRecipeViewModule(EditRecipeView view) {
        this.view = view;
    }

    @Provides
    EditRecipeView provideEditRecipeView() {
        return view;
    }
}
