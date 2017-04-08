package com.bnorm.barkeep.ui;

import com.bnorm.barkeep.AppScope;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class UiModule {

    @AppScope
    @Provides
    @UiScheduler
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @AppScope
    @Provides
    ViewContainer providesViewContainer() {
        return ViewContainer.DEFAULT;
    }
}
