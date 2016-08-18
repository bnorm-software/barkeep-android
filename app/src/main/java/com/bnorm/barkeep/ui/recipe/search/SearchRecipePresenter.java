package com.bnorm.barkeep.ui.recipe.search;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.GaeList;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import retrofit2.Response;
import rx.Scheduler;
import rx.functions.Action1;

@AppScope
public class SearchRecipePresenter extends AbstractPresenter<SearchRecipeView> {

    private final Action1<List<Recipe>> enqueueSearchResults = enqueue(SearchRecipeView::onSearchResults);
    private final Action1<Response<GaeList<Recipe>>> responseSearchResults = response -> enqueueSearchResults.call(
            response.body().getItems());
    private final Action1<Throwable> errorSearchResults = error -> enqueueSearchResults.call(Collections.emptyList());

    private final BarkeepService service;

    @Inject
    SearchRecipePresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
    }

    public void submit(String query) {
        service.getRecipes(query).subscribe(responseSearchResults, errorSearchResults);
    }
}
