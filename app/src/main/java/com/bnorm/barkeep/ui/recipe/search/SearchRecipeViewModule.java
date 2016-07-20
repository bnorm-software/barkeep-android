package com.bnorm.barkeep.ui.recipe.search;

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
}
