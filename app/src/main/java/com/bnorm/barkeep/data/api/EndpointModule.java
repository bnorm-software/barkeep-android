package com.bnorm.barkeep.data.api;

import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class EndpointModule {

    private final String rootUrl;
    private final boolean compression;

    public EndpointModule(String rootUrl, boolean compression) {
        this.rootUrl = rootUrl;
        this.compression = compression;
    }

    @EndpointScope
    @Provides
    Endpoint providesEndpoint() {
        Endpoint.Builder builder = new Endpoint.Builder(AndroidHttp.newCompatibleTransport(),
                                                        new AndroidJsonFactory(),
                                                        null);
        builder.setRootUrl(rootUrl);
        if (!compression) {
            builder.setGoogleClientRequestInitializer(request -> request.setDisableGZipContent(true));
        }
        return builder.build();
    }


    @EndpointScope
    @Provides
    @ApiScheduler
    Scheduler provideApiScheduler() {
        return Schedulers.io();
    }
}
