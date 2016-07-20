package com.bnorm.barkeep;

import android.content.Context;
import com.bnorm.barkeep.data.api.ApiComponent;
import com.bnorm.barkeep.data.api.ApiModule;
import com.bnorm.barkeep.data.api.DaggerApiComponent;
import com.bnorm.barkeep.data.api.net.DaggerNetComponent;
import com.bnorm.barkeep.data.api.net.NetComponent;
import com.bnorm.barkeep.data.api.net.NetModule;
import com.bnorm.barkeep.ui.DebugUiModule;
import okhttp3.HttpUrl;

final class AppInjector {
    private AppInjector() {
    }

    static AppComponent inject(Context context) {
        NetModule netModule = new NetModule(context.getCacheDir());
        NetComponent netComponent = DaggerNetComponent.builder().netModule(netModule).build();
        ApiModule apiModule = new ApiModule(HttpUrl.parse("https://bartender-1059.appspot.com/_ah/api/endpoint/v1/"));
        ApiComponent apiComponent = DaggerApiComponent.builder()
                                                      .netComponent(netComponent)
                                                      .apiModule(apiModule)
                                                      .build();

        return DaggerAppComponent.builder().apiComponent(apiComponent).uiModule(new DebugUiModule()).build();
    }
}
