package com.bnorm.barkeep.ui.bar.list;

import dagger.Module;
import dagger.Provides;

@Module
public class BarListViewModule {

    private final BarListView view;

    public BarListViewModule(BarListView view) {
        this.view = view;
    }

    @Provides
    BarListView providesBookListView() {
        return view;
    }
}
