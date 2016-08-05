package com.bnorm.barkeep;

import com.bnorm.barkeep.data.api.ApiComponent;
import com.bnorm.barkeep.ui.MainActivityComponent;
import com.bnorm.barkeep.ui.MainViewModule;
import com.bnorm.barkeep.ui.UiModule;
import com.bnorm.barkeep.ui.bar.BarDetailActivityComponent;
import com.bnorm.barkeep.ui.bar.BarDetailViewModule;
import com.bnorm.barkeep.ui.bar.BarListFragmentComponent;
import com.bnorm.barkeep.ui.bar.BarListViewModule;
import com.bnorm.barkeep.ui.bar.edit.EditBarActivityComponent;
import com.bnorm.barkeep.ui.bar.edit.EditBarViewModule;
import com.bnorm.barkeep.ui.book.BookDetailActivityComponent;
import com.bnorm.barkeep.ui.book.BookDetailViewModule;
import com.bnorm.barkeep.ui.book.BookListFragmentComponent;
import com.bnorm.barkeep.ui.book.BookListViewModule;
import com.bnorm.barkeep.ui.book.edit.EditBookActivityComponent;
import com.bnorm.barkeep.ui.book.edit.EditBookViewModule;
import com.bnorm.barkeep.ui.recipe.ViewRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.ViewRecipeViewModule;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeViewModule;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeActivityComponent;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeViewModule;
import dagger.Component;

@AppScope
@Component(modules = {UiModule.class}, dependencies = {ApiComponent.class})
public interface AppComponent extends ApiComponent {

    MainActivityComponent plus(MainViewModule module);

    BarDetailActivityComponent plus(BarDetailViewModule module);

    BarListFragmentComponent plus(BarListViewModule module);

    EditBarActivityComponent plus(EditBarViewModule module);

    BookDetailActivityComponent plus(BookDetailViewModule module);

    BookListFragmentComponent plus(BookListViewModule module);

    EditBookActivityComponent plus(EditBookViewModule module);

    ViewRecipeActivityComponent plus(ViewRecipeViewModule module);

    EditRecipeActivityComponent plus(EditRecipeViewModule module);

    SearchRecipeActivityComponent plus(SearchRecipeViewModule module);
}
