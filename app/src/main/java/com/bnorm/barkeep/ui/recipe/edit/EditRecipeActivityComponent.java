package com.bnorm.barkeep.ui.recipe.edit;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EditRecipeViewModule.class})
public interface EditRecipeActivityComponent {

    void inject(EditRecipeActivity activity);
}
