package com.bnorm.barkeep.data.api;

import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import dagger.Component;

@EndpointScope
@Component(modules = {EndpointModule.class}, dependencies = {})
public interface EndpointComponent {

    Endpoint endpoint();
}
