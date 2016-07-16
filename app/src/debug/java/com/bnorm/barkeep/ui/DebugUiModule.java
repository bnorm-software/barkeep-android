package com.bnorm.barkeep.ui;

public class DebugUiModule extends UiModule {

    @Override
    ViewContainer providesViewContainer() {
        return new DebugViewContainer();
    }
}
