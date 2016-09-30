package com.bnorm.barkeep.ui.bar.detail;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {BarDetailViewModule.class})
public interface BarDetailActivityComponent {

    void inject(BarDetailActivity activity);
}
