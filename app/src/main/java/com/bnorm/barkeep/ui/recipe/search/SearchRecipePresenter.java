package com.bnorm.barkeep.ui.recipe.search;

import java.util.Collections;
import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Scheduler;

@ActivityScope
public class SearchRecipePresenter {

    private final SearchRecipeView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public SearchRecipePresenter(SearchRecipeView view, BarkeepService service, @ApiScheduler Scheduler apiScheduler,
                                 @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.service = service;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void submit(String query) {
        service.getRecipes(query)
               .subscribeOn(apiScheduler)
               .observeOn(uiScheduler)
               .subscribe(response -> {
            view.onSearchResults(response.body().getItems());
        }, error -> {
            view.onSearchResults(Collections.emptyList());
        });
    }
}
