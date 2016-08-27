package com.bnorm.barkeep;

import android.app.Application;
import com.squareup.picasso.Picasso;

public class BarkeepApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = AppInjector.inject(getApplicationContext());

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.indicatorsEnabled(true);
        Picasso.setSingletonInstance(builder.build());
    }

    public AppComponent component() {
        return component;
    }
}
