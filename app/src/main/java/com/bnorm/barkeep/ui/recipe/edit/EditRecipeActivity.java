package com.bnorm.barkeep.ui.recipe.edit;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;

public class EditRecipeActivity extends BaseActivity
        implements EditRecipeView, ComponentDialogFragment.ComponentDialogListener {
    static final String RECIPE_TAG = EditRecipeActivity.class.getName() + ".recipe";

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.create_recipe_name) AppCompatEditText name;
    @BindView(R.id.create_recipe_description) AppCompatEditText description;
    @BindView(R.id.create_recipe_directions) AppCompatEditText directions;
    @BindView(R.id.create_recipe_components) RecyclerView components;

    // ===== Presenter ===== //

    @Inject EditRecipePresenter presenter;
    @Inject ComponentAdapter adapter;


    public static void launch(Context source, Recipe recipe) {
        Intent intent = new Intent(source, EditRecipeActivity.class);
        intent.putExtra(RECIPE_TAG, recipe);
        source.startActivity(intent);
    }

    public static void launch(Context source) {
        Intent intent = new Intent(source, EditRecipeActivity.class);
        source.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barkeep().component().plus(new EditRecipeViewModule(this)).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_create_recipe, container);
        ButterKnife.bind(this, container);

        setSupportActionBar(toolbar);
        components.setAdapter(adapter);
        components.setNestedScrollingEnabled(false);
        components.setItemAnimator(null);
        components.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadRecipe(getIntent().getParcelableExtra(RECIPE_TAG));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    public void onStop() {
        presenter.detach();
        super.onStop();
    }

    @NonNull
    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(name.getText().toString());
        recipe.setDescription(description.getText().toString());
        recipe.setDirections(directions.getText().toString());
        recipe.setComponents(adapter.getItems());
        return recipe;
    }

    private void loadRecipe(@Nullable Recipe recipe) {
        if (recipe != null) {
            name.setText(recipe.getName());
            description.setText(recipe.getDescription());
            directions.setText(recipe.getDirections());
            adapter.set(recipe.getComponents());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.create_recipe_cancel)
    void onCancel() {
        // todo(bnorm) are you sure? - if there are changed fields
        onBackPressed();
    }

    @OnClick(R.id.create_recipe_save)
    void onSave() {
        // todo(bnorm) check fields are properly filled
        presenter.save(getRecipe());
    }

    @OnClick(R.id.create_recipe_add_component)
    void onAddComponent() {
        onEditComponent(null, new Component(), "Cancel");
    }

    @Override
    public void onDialogPositiveClick(Integer i, Component component) {
        if (i == null) {
            adapter.add(component);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        } else {
            adapter.set(i, component);
            adapter.notifyItemChanged(i);
        }
    }

    @Override
    public void onDialogNegativeClick(Integer i, Component component) {
        if (i != null) {
            adapter.remove(i);
            adapter.notifyItemRemoved(i);
        }
        // else { canceled adding a new component }
    }

    @Override
    public void onRecipeSaved(Recipe recipe) {
        Toast.makeText(getApplicationContext(), "Saved " + recipe.getName(), Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    @Override
    public void onEditComponent(Integer position, Component component, String negativeText) {
        // if position is null, it is a new component
        ComponentDialogFragment.launch(getSupportFragmentManager(), position, component, negativeText);
    }
}
