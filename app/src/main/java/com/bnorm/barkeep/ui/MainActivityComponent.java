package com.bnorm.barkeep.ui;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {MainViewModule.class})
public interface MainActivityComponent {

    void inject(MainActivity activity);
}
