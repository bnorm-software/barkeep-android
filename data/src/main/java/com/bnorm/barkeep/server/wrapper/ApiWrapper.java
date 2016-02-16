package com.bnorm.barkeep.server.wrapper;

import java.io.IOException;

import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

public abstract class ApiWrapper {

    private final RemoteApiInstaller installer;

    protected ApiWrapper() {
        this.installer = new RemoteApiInstaller();
    }

    public abstract RemoteApiOptions createOptions();

    public void initialize() throws IOException {
        installer.install(createOptions());
    }

    public void terminate() {
        installer.uninstall();
    }
}
