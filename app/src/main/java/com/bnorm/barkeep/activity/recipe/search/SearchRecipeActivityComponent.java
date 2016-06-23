package com.bnorm.barkeep.activity.recipe.search;

import com.bnorm.barkeep.inject.activity.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {SearchRecipeViewModule.class})
public interface SearchRecipeActivityComponent {

    void inject(SearchRecipeActivity activity);
}
