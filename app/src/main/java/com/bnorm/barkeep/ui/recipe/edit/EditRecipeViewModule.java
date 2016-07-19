package com.bnorm.barkeep.ui.recipe.edit;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

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

    @ActivityScope
    @Provides
    EditRecipePresenter provideEditRecipeActivityPresenter(Endpoint endpoint, @ApiScheduler Scheduler apiScheduler,
                                                           @UiScheduler Scheduler uiScheduler) {
        return new EditRecipePresenter(view, endpoint, apiScheduler, uiScheduler);
    }
}
