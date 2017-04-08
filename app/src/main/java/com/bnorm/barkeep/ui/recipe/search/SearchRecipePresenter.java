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
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

@AppScope
public class SearchRecipePresenter extends AbstractPresenter<SearchRecipeView> {

    private final Consumer<List<Recipe>> enqueueSearchResults = enqueue(SearchRecipeView::onSearchResults);
    private final Consumer<Response<GaeList<Recipe>>> responseSearchResults = response -> enqueueSearchResults.accept(
            response.body().getItems());
    private final Consumer<Throwable> errorSearchResults = error -> enqueueSearchResults.accept(Collections.emptyList());

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
