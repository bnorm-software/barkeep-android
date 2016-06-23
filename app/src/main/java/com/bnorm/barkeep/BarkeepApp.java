package com.bnorm.barkeep;

import android.app.Application;
import com.bnorm.barkeep.data.api.DaggerEndpointComponent;
import com.bnorm.barkeep.data.api.EndpointComponent;
import com.bnorm.barkeep.data.api.EndpointModule;

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
