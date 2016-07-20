package com.bnorm.barkeep.data.api;

import com.bnorm.barkeep.data.api.net.NetComponent;
import dagger.Component;
import rx.Scheduler;

@ApiScope
@Component(modules = {ApiModule.class}, dependencies = {NetComponent.class})
public interface ApiComponent {

    BarkeepService service();

    @ApiScheduler
    Scheduler apiScheduler();
}
