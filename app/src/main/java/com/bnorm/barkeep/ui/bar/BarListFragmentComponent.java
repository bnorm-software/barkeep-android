package com.bnorm.barkeep.ui.bar;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {BarListViewModule.class})
public interface BarListFragmentComponent {

    void inject(BarListFragment fragment);
}
