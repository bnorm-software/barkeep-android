package com.bnorm.barkeep.server.wrapper;

import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class LocalWrapper extends ApiWrapper {

    private final String host;
    private final int port;

    public LocalWrapper() {
        this.host = "localhost";
        this.port = 8080;
    }

    @Override
    public RemoteApiOptions createOptions() {
        RemoteApiOptions options = new RemoteApiOptions();
        options.server(host, port);
        options.useDevelopmentServerCredential();
        return options;
    }
}
