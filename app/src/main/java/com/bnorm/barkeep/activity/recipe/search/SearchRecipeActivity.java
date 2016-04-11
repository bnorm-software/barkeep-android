package com.bnorm.barkeep.activity.recipe.search;

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
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.task.RecipeListAsyncTask;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;

public class SearchRecipeActivity extends BaseActivity {

    // ===== Model ===== //

    private RecipeAdapter mSearchResultsAdapter;


    // ===== View ===== //

    @Bind(R.id.recipe_search_results) RecyclerView mSearchResults;
    @Bind(R.id.search) SearchView mSearchView;


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
        mSearchResults.setLayoutManager(new WrappingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecipeListAsyncTask task = new RecipeListAsyncTask() {
                    @Override
                    protected void onPostExecute(List<Recipe> result) {
                        mSearchResultsAdapter.set(result);
                        mSearchResultsAdapter.notifyDataSetChanged();
                    }
                };
                task.execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
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
