package com.bnorm.barkeep.ui.bar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Observable;
import rx.Scheduler;

@ActivityScope
public class BarListPresenter {

    private final BarListView view;
    private final Endpoint endpoint;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public BarListPresenter(BarListView view, Endpoint endpoint, @ApiScheduler Scheduler apiScheduler,
                            @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.endpoint = endpoint;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void loadBars() {
        Observable.fromCallable(() -> {
            try {
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
            } catch (IOException ignore) {
                return Collections.<Bar>emptyList();
            }
        }).subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(result -> {
            view.onBars(result);
        });
    }
}
