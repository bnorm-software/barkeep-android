package com.bnorm.barkeep.ui.recipe.edit;

import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

@AppScope
public class EditRecipePresenter extends AbstractPresenter<EditRecipeView> {

    private final Consumer<Recipe> enqueueRecipeSaved = enqueue(EditRecipeView::onRecipeSaved);
    private final Consumer<Response<Recipe>> responseRecipeSaved = response -> enqueueRecipeSaved.accept(response.body());

    private final BarkeepService service;

    @Inject
    EditRecipePresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
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
                   .subscribe(responseRecipeSaved);
            return true;
        } else {
            return false;
        }
    }
}
