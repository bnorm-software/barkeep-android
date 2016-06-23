package com.bnorm.barkeep.activity.recipe.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.google.common.base.Preconditions;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchRecipePresenter {

    private final SearchRecipeView view;
    private final Endpoint endpoint;

    public SearchRecipePresenter(SearchRecipeView view, Endpoint endpoint) {
        this.view = view;
        this.endpoint = endpoint;
    }

    public void submit(String query) {
        Observable.fromCallable(() -> {
            String name = Preconditions.checkNotNull(query);
            try {
                List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe> recipes;
                recipes = endpoint.listRecipes().setName(name).execute().getItems();
                if (recipes != null) {
                    List<Recipe> items = new ArrayList<>();
                    for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe recipe : recipes) {
                        items.add(new Recipe(recipe));
                    }
                    return items;
                } else {
                    return Collections.<Recipe>emptyList();
                }
            } catch (IOException e) {
                return Collections.<Recipe>emptyList();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::onSearchResults);
    }
}
