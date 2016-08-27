package com.bnorm.barkeep.ui.book;

import dagger.Module;
import dagger.Provides;

@Module
public class BookListViewModule {

    private final BookListView view;

    public BookListViewModule(BookListView view) {
        this.view = view;
    }

    @Provides
    BookListView providesBookListView() {
        return view;
    }
}
