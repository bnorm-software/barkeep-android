package com.bnorm.barkeep.ui;

import com.bnorm.barkeep.AppScope;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class UiModule {

    @AppScope
    @Provides
    @UiScheduler
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
