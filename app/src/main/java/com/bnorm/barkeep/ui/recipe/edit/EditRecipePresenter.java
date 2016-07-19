package com.bnorm.barkeep.ui.recipe.edit;

import java.io.IOException;

import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import rx.Observable;
import rx.Scheduler;

public class EditRecipePresenter {

    private final EditRecipeView view;
    private final Endpoint endpoint;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    public EditRecipePresenter(EditRecipeView view, Endpoint endpoint, Scheduler apiScheduler, Scheduler uiScheduler) {
        this.view = view;
        this.endpoint = endpoint;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void cancel() {
        // todo are you sure popup
        view.onClose();
    }

    private boolean validate(Recipe recipe) {
        boolean malformed = recipe == null //
                || recipe.getName() == null //
                || recipe.getNameWords() == null //
                || recipe.getComponents() == null;
        return !malformed;
    }

    public boolean save(Recipe recipe) {
        if (validate(recipe)) {
            Observable.fromCallable(() -> {
                Recipe response;
                try {
                    endpoint.getRecipe(recipe.getName()).execute();
                    response = new Recipe(endpoint.updateRecipe(recipe.getName(), recipe.toStore()).execute());
                } catch (IOException e) {
                    response = new Recipe(endpoint.insertRecipe(recipe.toStore()).execute());
                }
                return response;
            }).subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(response -> {
                view.onRecipeSaved(response);
                view.onClose();
            });
            return true;
        } else {
            return false;
        }
    }

    public void addComponent() {
        view.onEditComponent(null, new Component(), "Cancel");
    }
}
