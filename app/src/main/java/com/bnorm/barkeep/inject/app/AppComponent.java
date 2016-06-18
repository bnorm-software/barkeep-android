package com.bnorm.barkeep.inject.app;

import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivityComponent;
import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivityModule;
import com.bnorm.barkeep.inject.endpoint.EndpointComponent;
import dagger.Component;

@AppScope
@Component(modules = {}, dependencies = {EndpointComponent.class})
public interface AppComponent extends EndpointComponent {

    EditRecipeActivityComponent plus(EditRecipeActivityModule module);
}
