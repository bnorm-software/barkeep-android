package com.bnorm.barkeep.ui.recipe.edit;

import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Scheduler;

@ActivityScope
public class EditRecipePresenter {

    private final EditRecipeView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public EditRecipePresenter(EditRecipeView view, BarkeepService service, @ApiScheduler Scheduler apiScheduler,
                               @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.service = service;
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
            service.getRecipe(recipe.getName())
                   .flatMap(response -> response.isSuccessful() ? service.updateRecipe(recipe.getName(), recipe)
                                                                : service.createRecipe(recipe))
                   .subscribeOn(apiScheduler)
                   .observeOn(uiScheduler)
                   .subscribe(response -> {
                       view.onRecipeSaved(response.body());
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
