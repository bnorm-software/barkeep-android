package com.bnorm.barkeep;

import com.bnorm.barkeep.data.api.EndpointComponent;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeViewModule;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeViewModule;
import dagger.Component;

@AppScope
@Component(modules = {}, dependencies = {EndpointComponent.class})
public interface AppComponent extends EndpointComponent {

    EditRecipeActivityComponent plus(EditRecipeViewModule module);

    SearchRecipeActivityComponent plus(SearchRecipeViewModule module);
}
