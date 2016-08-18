package com.bnorm.barkeep.ui.bar;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.data.api.model.GaeList;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import retrofit2.Response;
import rx.Scheduler;
import rx.functions.Action1;

@AppScope
public class BarListPresenter extends AbstractPresenter<BarListView> {

    private final Action1<List<Bar>> enqueueBars = enqueue(BarListView::onBars);
    private Action1<Response<GaeList<Bar>>> responseBars = response -> enqueueBars.call(response.body().getItems());
    private Action1<Throwable> errorBars = error -> enqueueBars.call(Collections.emptyList());

    private final BarkeepService service;

    @Inject
    BarListPresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
    }

    public void loadBars() {
        service.getBars().subscribe(responseBars, errorBars);
    }
}
