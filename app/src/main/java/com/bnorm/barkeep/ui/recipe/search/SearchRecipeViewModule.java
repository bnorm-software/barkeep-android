package com.bnorm.barkeep.ui.recipe.search;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class SearchRecipeViewModule {

    private final SearchRecipeView view;

    public SearchRecipeViewModule(SearchRecipeView view) {
        this.view = view;
    }

    @Provides
    SearchRecipeView provideSearchRecipeView() {
        return view;
    }

    @ActivityScope
    @Provides
    SearchRecipePresenter provideSearchRecipePresenter(Endpoint endpoint, @ApiScheduler Scheduler apiScheduler,
                                                       @UiScheduler Scheduler uiScheduler) {
        return new SearchRecipePresenter(view, endpoint, apiScheduler, uiScheduler);
    }
}
