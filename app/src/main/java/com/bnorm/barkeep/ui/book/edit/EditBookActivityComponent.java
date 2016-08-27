package com.bnorm.barkeep.ui.book.edit;

import com.bnorm.barkeep.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EditBookViewModule.class})
public interface EditBookActivityComponent {

    void inject(EditBookActivity activity);
}
