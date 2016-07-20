package com.bnorm.barkeep.ui.bar.edit;

import dagger.Module;
import dagger.Provides;

@Module
public class EditBarViewModule {

    private final EditBarView view;

    public EditBarViewModule(EditBarView view) {
        this.view = view;
    }

    @Provides
    EditBarView provideEditBookView() {
        return view;
    }
}
