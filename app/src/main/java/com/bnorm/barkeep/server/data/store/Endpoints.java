package com.bnorm.barkeep.server.data.store;

import java.io.IOException;

import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;

public enum Endpoints {
    instance;

    private Endpoint recipeEndpoint;

    Endpoints() {
        recipeEndpoint = null;
    }

    public Endpoint getEndpoint() {
        if (recipeEndpoint == null) {
            synchronized (this) {
                if (recipeEndpoint == null) {
                    recipeEndpoint = setup(new Endpoint.Builder(AndroidHttp.newCompatibleTransport(),
                                                                new AndroidJsonFactory(),
                                                                null)).build();
                }
            }
        }
        return recipeEndpoint;
    }

    private static <B extends AbstractGoogleJsonClient.Builder> B setup(B builder) {
        // - turn off compression when running against local devappserver
        //        builder.setRootUrl("https://bartender-1059.appspot.com/_ah/api/");

        builder.setRootUrl("http://192.168.1.11:8080/_ah/api/"); // laptop
//        builder.setRootUrl("http://10.0.2.2:8080/_ah/api/"); // emulator
        builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                abstractGoogleClientRequest.setDisableGZipContent(true);
            }
        });
        return builder;
    }
}
