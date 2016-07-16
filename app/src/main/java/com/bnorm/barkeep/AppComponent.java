package com.bnorm.barkeep;

import com.bnorm.barkeep.data.api.EndpointComponent;
import com.bnorm.barkeep.ui.MainActivityComponent;
import com.bnorm.barkeep.ui.MainViewModule;
import com.bnorm.barkeep.ui.UiModule;
import com.bnorm.barkeep.ui.bar.BarDetailActivityComponent;
import com.bnorm.barkeep.ui.bar.BarDetailViewModule;
import com.bnorm.barkeep.ui.book.BookDetailActivityComponent;
import com.bnorm.barkeep.ui.book.BookDetailViewModule;
import com.bnorm.barkeep.ui.recipe.ViewRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.ViewRecipeViewModule;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeViewModule;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeViewModule;
import dagger.Component;

@AppScope
@Component(modules = {UiModule.class}, dependencies = {EndpointComponent.class})
public interface AppComponent extends EndpointComponent {

    MainActivityComponent plus(MainViewModule module);

    BarDetailActivityComponent plus(BarDetailViewModule module);

    BookDetailActivityComponent plus(BookDetailViewModule module);

    ViewRecipeActivityComponent plus(ViewRecipeViewModule module);

    EditRecipeActivityComponent plus(EditRecipeViewModule module);

    SearchRecipeActivityComponent plus(SearchRecipeViewModule module);
}
