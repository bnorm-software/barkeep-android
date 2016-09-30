package com.bnorm.barkeep.ui.recipe.search;

import java.util.List;
import javax.inject.Inject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.recipe.detail.ViewRecipeActivity;

public class SearchRecipeActivity extends BaseActivity implements SearchRecipeView, SearchView.OnQueryTextListener {

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.search) SearchView searchView;
    @BindView(R.id.recipe_search_results) RecyclerView results;

    // ===== Presenter ===== //

    @Inject SearchRecipePresenter presenter;
    @Inject RecipeAdapter adapter;


    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchRecipeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barkeep().component().plus(new SearchRecipeViewModule(this)).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_search_recipe, container);
        ButterKnife.bind(this, container);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        results.setAdapter(adapter);
        results.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.submit(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // what to do here?
        return false;
    }

    @Override
    public void onRecipeView(Recipe recipe) {
        ViewRecipeActivity.launch(this, recipe);
    }

    @Override
    public void onSearchResults(List<Recipe> results) {
        adapter.set(results);
        adapter.notifyDataSetChanged();
    }
}
