package com.bnorm.barkeep;

import com.bnorm.barkeep.data.api.DaggerEndpointComponent;
import com.bnorm.barkeep.data.api.EndpointComponent;
import com.bnorm.barkeep.data.api.EndpointModule;
import com.bnorm.barkeep.ui.DebugUiModule;

final class AppInjector {
    private AppInjector() {
    }

    static AppComponent inject() {
        EndpointModule endpointModule = new EndpointModule("https://bartender-1059.appspot.com/_ah/api/", true);
        EndpointComponent endpointComponent = DaggerEndpointComponent.builder().endpointModule(endpointModule).build();

        return DaggerAppComponent.builder().endpointComponent(endpointComponent).uiModule(new DebugUiModule()).build();
    }
}
