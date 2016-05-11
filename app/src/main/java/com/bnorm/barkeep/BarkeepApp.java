package com.bnorm.barkeep;

import android.app.Application;
import com.bnorm.barkeep.inject.app.AppComponent;
import com.bnorm.barkeep.inject.app.DaggerAppComponent;
import com.bnorm.barkeep.inject.endpoint.DaggerEndpointComponent;
import com.bnorm.barkeep.inject.endpoint.EndpointComponent;
import com.bnorm.barkeep.inject.endpoint.EndpointModule;

public class BarkeepApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        EndpointModule endpointModule = new EndpointModule("https://bartender-1059.appspot.com/_ah/api/", true);
        EndpointComponent endpointComponent = DaggerEndpointComponent.builder().endpointModule(endpointModule).build();

        component = DaggerAppComponent.builder().endpointComponent(endpointComponent).build();
    }

    public AppComponent component() {
        return component;
    }
}
