package com.bnorm.barkeep.ui.recipe.edit;

import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class EditRecipeViewModule {

    private final EditRecipeView view;
    private final Recipe recipe;

    public EditRecipeViewModule(EditRecipeView view, Recipe recipe) {
        this.view = view;
        this.recipe = recipe;
    }

    @Provides
    EditRecipeView provideEditRecipeView() {
        return view;
    }

    @ActivityScope
    @Provides
    EditRecipePresenter provideEditRecipeActivityPresenter(Endpoint endpoint) {
        return new EditRecipePresenter(view, endpoint, recipe);
    }
}
