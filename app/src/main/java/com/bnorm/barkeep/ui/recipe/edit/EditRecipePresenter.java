package com.bnorm.barkeep.ui.recipe.edit;

import java.io.IOException;

import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditRecipePresenter {

    private final EditRecipeView view;
    private final Endpoint endpoint;
    private final Recipe recipe;

    public EditRecipePresenter(EditRecipeView view, Endpoint endpoint, Recipe recipe) {
        this.view = view;
        this.endpoint = endpoint;
        this.recipe = recipe != null ? recipe : new Recipe();
    }

    public void cancel() {
        // todo are you sure popup
        view.onClose();
    }

    public boolean validate() {
        // todo(bnorman) validate all required fields
        return true;
    }

    public void save() {
        if (validate()) {
            updateRecipe();
            Observable.fromCallable(() -> {
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
                view.onRecipeSaved(recipe);
                view.onClose();
            });
        }
    }

    public void addComponent() {
        view.onComponentDialog(null, new Component(), "Cancel");
    }

    public void updateRecipe() {
        recipe.setName(view.getName());
        recipe.setDescription(view.getDescription());
        recipe.setDirections(view.getDirections());
        recipe.setComponents(view.getComponents());
    }

    public Recipe recipe() {
        return recipe;
    }
}
