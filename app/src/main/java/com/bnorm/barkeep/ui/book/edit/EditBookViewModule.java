package com.bnorm.barkeep.ui.book.edit;

import dagger.Module;
import dagger.Provides;

@Module
public class EditBookViewModule {

    private final EditBookView view;

    public EditBookViewModule(EditBookView view) {
        this.view = view;
    }

    @Provides
    EditBookView provideEditBookView() {
        return view;
    }
}