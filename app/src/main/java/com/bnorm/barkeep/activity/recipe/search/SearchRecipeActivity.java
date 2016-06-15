package com.bnorm.barkeep.activity.recipe.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;
import com.google.common.base.Preconditions;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.support.v7.widget.SearchViewQueryTextEvent;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchRecipeActivity extends BaseActivity {

    // ===== Model ===== //

    private RecipeAdapter mSearchResultsAdapter;


    // ===== View ===== //

    @BindView(R.id.recipe_search_results) RecyclerView mSearchResults;
    @BindView(R.id.search) SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_recipe);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSearchResultsAdapter = new RecipeAdapter(this);

        mSearchResults.setAdapter(mSearchResultsAdapter);
        mSearchResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mSearchView.onActionViewExpanded();
        RxSearchView.queryTextChangeEvents(mSearchView)
                    .filter(SearchViewQueryTextEvent::isSubmitted)
                    .flatMap(event -> Observable.<List<Recipe>>fromCallable(() -> {
                        String name = Preconditions.checkNotNull(event.queryText().toString());
                        try {
                            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe> recipes;
                            recipes = component().endpoint().listRecipes().setName(name).execute().getItems();
                            if (recipes != null) {
                                List<Recipe> items = new ArrayList<>();
                                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe recipe : recipes) {
                                    items.add(new Recipe(recipe));
                                }
                                return items;
                            } else {
                                return Collections.emptyList();
                            }
                        } catch (IOException e) {
                            return Collections.emptyList();
                        }
                    }).subscribeOn(Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        mSearchResultsAdapter.set(result);
                        mSearchResultsAdapter.notifyDataSetChanged();
                    });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
