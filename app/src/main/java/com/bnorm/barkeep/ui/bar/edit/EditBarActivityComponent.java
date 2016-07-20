package com.bnorm.barkeep.ui.bar.edit;

import com.bnorm.barkeep.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {EditBarViewModule.class})
public interface EditBarActivityComponent {

    void inject(EditBarActivity activity);
}
