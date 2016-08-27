package com.bnorm.barkeep.ui.bar;

import java.util.Collections;
import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Scheduler;

@ActivityScope
public class BarListPresenter {

    private final BarListView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public BarListPresenter(BarListView view, BarkeepService service, @ApiScheduler Scheduler apiScheduler,
                            @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.service = service;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void loadBars() {
        service.getBars().subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(response -> {
            view.onBars(response.body().getItems());
        }, error -> {
            view.onBars(Collections.emptyList());
        });
    }
}
