package com.bnorm.barkeep.activity.recipe.edit;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;

public class EditRecipeActivity extends BaseActivity {
    public static final String RECIPE_TAG = EditRecipeActivity.class.getName() + ".recipe";

    // ===== View ===== //

    @BindView(R.id.create_recipe_name) AppCompatEditText name;
    @BindView(R.id.create_recipe_description) AppCompatEditText description;
    @BindView(R.id.create_recipe_directions) AppCompatEditText directions;
    @BindView(R.id.create_recipe_components) RecyclerView components;

    // ===== Presenter ===== //

    @Inject EditRecipeActivityPresenter presenter;
    @Inject ComponentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        ButterKnife.bind(this);
        barkeep().component().inject(this);

        // ===== Configure toolbar ===== //

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ===== Configure ? ===== //

        components.setAdapter(adapter);
        components.setNestedScrollingEnabled(false);
        components.setItemAnimator(null);
        components.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Recipe recipe = presenter.recipe();
        name.setText(recipe.getName());
        description.setText(recipe.getDescription());
        directions.setText(recipe.getDirections());
        adapter.set(recipe.getComponents());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.updateRecipe();
    }

    @OnClick(R.id.create_recipe_cancel)
    void onCancel() {
        presenter.cancel();
    }

    @OnClick(R.id.create_recipe_save)
    void onSave() {
        presenter.save();
    }

    @OnClick(R.id.create_recipe_add_component)
    void onAddComponent() {
        presenter.addComponent();
    }
}
