package com.bnorm.barkeep.activity.recipe.edit;

import android.os.Bundle;
import com.bnorm.barkeep.inject.activity.ActivityScope;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import dagger.Module;
import dagger.Provides;

@Module
public class EditRecipeActivityModule {

    private final EditRecipeView activity;
    private final Recipe recipe;

    public EditRecipeActivityModule(EditRecipeActivity activity) {
        this.activity = activity;

        Bundle bundle = activity.getIntent().getExtras();
        this.recipe = bundle != null ? bundle.getParcelable(EditRecipeView.RECIPE_TAG) : null;
    }

    @ActivityScope
    @Provides
    EditRecipeActivityPresenter provideEditRecipeActivityPresenter(Endpoint endpoint) {
        return new EditRecipeActivityPresenter(activity, endpoint, recipe);
    }
}
