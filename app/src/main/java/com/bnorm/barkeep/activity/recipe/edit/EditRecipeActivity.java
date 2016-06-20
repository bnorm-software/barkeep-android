package com.bnorm.barkeep.activity.recipe.edit;

import java.util.List;
import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;

public class EditRecipeActivity extends BaseActivity
        implements EditRecipeView, ComponentDialogFragment.ComponentDialogListener {
    private static final String RECIPE_TAG = EditRecipeView.class.getName() + ".recipe";

    // ===== View ===== //

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Bundle bundle = getIntent().getExtras();
        Recipe recipe = bundle != null ? bundle.getParcelable(RECIPE_TAG) : null;

        ButterKnife.bind(this);
        barkeep().component().plus(new EditRecipeViewModule(this, recipe)).inject(this);

        setSupportActionBar(toolbar);
        components.setAdapter(adapter);
        components.setNestedScrollingEnabled(false);
        components.setItemAnimator(null);
        components.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recipe = presenter.recipe();
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
    public void onClose() {
        onBackPressed();
    }

    @Override
    public void onRecipeSaved(Recipe recipe) {
        Toast.makeText(getApplicationContext(), "Saved " + recipe.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComponentDialog(Integer position, Component component, String negativeText) {
        ComponentDialogFragment.launch(getSupportFragmentManager(), position, component, negativeText);
    }

    @Override
    public String getName() {
        return name.getText().toString();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public String getDirections() {
        return directions.getText().toString();
    }

    @Override
    public List<Component> getComponents() {
        return adapter.getItems();
    }
}
