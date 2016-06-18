package com.bnorm.barkeep.activity.recipe.edit;

import java.io.IOException;

import android.os.Bundle;
import android.widget.Toast;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditRecipeActivityPresenter implements ComponentDialogFragment.ComponentDialogListener {

    private final EditRecipeActivity view;
    private final ComponentAdapter adapter;
    private final Endpoint endpoint;
    private final Recipe recipe;

    public EditRecipeActivityPresenter(EditRecipeActivity view, ComponentAdapter adapter, Endpoint endpoint,
                                       Recipe recipe) {
        this.view = view;
        this.adapter = adapter;
        this.endpoint = endpoint;
        this.recipe = recipe != null ? recipe : new Recipe();
    }

    public void close() {
        view.onBackPressed();
    }

    public void cancel() {
        // todo are you sure popup
        close();
    }

    public boolean validate() {
        // todo(bnorman) validate all required fields
        return true;
    }

    public void save() {
        if (validate()) {
            Observable.fromCallable(() -> {
                updateRecipe();
                boolean exists = true;
                try {
                    endpoint.getRecipe(recipe.getName()).execute();
                } catch (IOException e) {
                    exists = false;
                }
                if (exists) {
                    endpoint.updateRecipe(recipe.getName(), recipe.toStore()).execute();
                } else {
                    endpoint.insertRecipe(recipe.toStore()).execute();
                }
                return recipe;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(recipe -> {
                Toast.makeText(view.getApplicationContext(), "Saved " + recipe.getName(), Toast.LENGTH_LONG).show();
                close();
            });
        }
    }

    public void addComponent() {
        Bundle args = new Bundle();
        args.putString(ComponentDialogFragment.NEGATIVE_TEXT_ARG, "Cancel");

        ComponentDialogFragment dialog = new ComponentDialogFragment();
        dialog.setArguments(args);
        dialog.setComponent(new Component());
        dialog.show(view.getSupportFragmentManager(), "ComponentDialogFragment");
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
        // else canceled adding a new component
    }

    public void updateRecipe() {
        // todo how do we update the recipe?
        // don't like pulling the information out of the view like this
        recipe.setName(view.name.getText().toString());
        recipe.setName(view.description.getText().toString());
        recipe.setName(view.directions.getText().toString());
        recipe.setComponents(adapter.getItems());
    }

    public Recipe recipe() {
        return recipe;
    }
}
