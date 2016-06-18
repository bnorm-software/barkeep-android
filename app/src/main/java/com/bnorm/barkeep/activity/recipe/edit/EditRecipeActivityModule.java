package com.bnorm.barkeep.activity.recipe.edit;

import android.os.Bundle;
import com.bnorm.barkeep.inject.activity.ActivityScope;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import dagger.Module;
import dagger.Provides;

@Module
public class EditRecipeActivityModule {

    private final EditRecipeActivity activity;
    private final Recipe recipe;

    public EditRecipeActivityModule(EditRecipeActivity activity) {
        this.activity = activity;

        Bundle bundle = activity.getIntent().getExtras();
        this.recipe = bundle != null ? bundle.getParcelable(EditRecipeActivity.RECIPE_TAG) : null;
    }

    @ActivityScope
    @Provides
    EditRecipeActivityPresenter provideEditRecipeActivityPresenter(ComponentAdapter adapter, Endpoint endpoint) {
        return new EditRecipeActivityPresenter(activity, adapter, endpoint, recipe);
    }

    @ActivityScope
    @Provides
    ComponentAdapter provideComponentAdapter() {
        return new ComponentAdapter(activity);
    }
}
