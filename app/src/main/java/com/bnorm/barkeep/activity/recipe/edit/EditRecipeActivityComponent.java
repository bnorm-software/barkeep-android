package com.bnorm.barkeep.activity.recipe.edit;

import com.bnorm.barkeep.inject.activity.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EditRecipeActivityModule.class})
public interface EditRecipeActivityComponent {

    void inject(EditRecipeActivity activity);
}
