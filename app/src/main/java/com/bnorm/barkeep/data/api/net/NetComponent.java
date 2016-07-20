package com.bnorm.barkeep.data.api.net;

import dagger.Component;
import okhttp3.OkHttpClient;

@NetScope
@Component(modules = {NetModule.class}, dependencies = {})
public interface NetComponent {

    OkHttpClient client();
}
