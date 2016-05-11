package com.bnorm.barkeep;

import android.app.Application;
import com.bnorm.barkeep.inject.app.AppComponent;
import com.bnorm.barkeep.inject.app.DaggerAppComponent;
import com.bnorm.barkeep.inject.endpoint.DaggerEndpointComponent;
import com.bnorm.barkeep.inject.endpoint.EndpointComponent;
import com.bnorm.barkeep.inject.endpoint.EndpointModule;

public class BarkeepApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

         EndpointModule endpointModule = new EndpointModule("https://bartender-1059.appspot.com/_ah/api/", true);
        // EndpointModule endpointModule = new EndpointModule("http://192.168.1.4:8080/_ah/api/", false); // laptop
        // EndpointModule endpointModule = new EndpointModule("http://10.0.2.2:8080/_ah/api/", false); // emulator
        // EndpointModule endpointModule = new EndpointModule("http://10.10.20.1:8080/_ah/api/", false); // jer
        EndpointComponent endpointComponent = DaggerEndpointComponent.builder().endpointModule(endpointModule).build();

        appComponent = DaggerAppComponent.builder().endpointComponent(endpointComponent).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
