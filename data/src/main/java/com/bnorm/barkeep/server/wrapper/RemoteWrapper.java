package com.bnorm.barkeep.server.wrapper;

import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public class RemoteWrapper extends ApiWrapper {

    private final String host;
    private final int port;

    public RemoteWrapper() {
        this.host = "bartender-1059.appspot.com";
        this.port = 443;
    }

    @Override
    public RemoteApiOptions createOptions() {
        RemoteApiOptions options = new RemoteApiOptions();
        options.server(host, port);
        options.useApplicationDefaultCredential();
        return options;
    }
}
