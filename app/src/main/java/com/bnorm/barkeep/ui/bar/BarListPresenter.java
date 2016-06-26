package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import rx.Observable;
import rx.Scheduler;

public class BarListPresenter {

    private final BarListView view;
    private final Endpoint endpoint;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    public BarListPresenter(BarListView view, Endpoint endpoint, Scheduler apiScheduler, Scheduler uiScheduler) {
        this.view = view;
        this.endpoint = endpoint;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void loadBars() {
        Observable.fromCallable(() -> {
            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar> bars;
            bars = endpoint.listBars().execute().getItems();
            if (bars != null) {
                List<Bar> result = new ArrayList<>();
                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar bar : bars) {
                    result.add(new Bar(bar));
                }
                return result;
            } else {
                return Collections.<Bar>emptyList();
            }
        }).subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(result -> {
            view.onBars(result);
        });
    }
}
