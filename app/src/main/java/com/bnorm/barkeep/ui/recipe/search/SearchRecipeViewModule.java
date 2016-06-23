package com.bnorm.barkeep.ui.recipe.search;

import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Module;
import dagger.Provides;

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
    SearchRecipePresenter provideSearchRecipePresenter(Endpoint endpoint) {
        return new SearchRecipePresenter(view, endpoint);
    }
}
