package com.bnorm.barkeep.ui.recipe.detail;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ViewRecipeViewModule.class})
public interface ViewRecipeActivityComponent {

    void inject(ViewRecipeActivity activity);
}
