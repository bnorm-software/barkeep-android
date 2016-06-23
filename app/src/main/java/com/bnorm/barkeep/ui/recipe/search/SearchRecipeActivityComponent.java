package com.bnorm.barkeep.ui.recipe.search;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {SearchRecipeViewModule.class})
public interface SearchRecipeActivityComponent {

    void inject(SearchRecipeActivity activity);
}
