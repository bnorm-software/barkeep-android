package com.bnorm.barkeep;

import android.app.Application;

public class BarkeepApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = AppInjector.inject(getApplicationContext());
    }

    public AppComponent component() {
        return component;
    }
}
