package com.bnorm.barkeep.ui;

import com.bnorm.barkeep.AppScope;
import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

    @AppScope
    @Provides
    ViewContainer providesViewContainer() {
        return ViewContainer.DEFAULT;
    }
}
