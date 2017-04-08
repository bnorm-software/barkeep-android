package com.bnorm.barkeep.ui.bar.list;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.data.api.model.GaeList;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

@AppScope
public class BarListPresenter extends AbstractPresenter<BarListView> {

    private final Consumer<List<Bar>> enqueueBars = enqueue(BarListView::onBars);
    private final Consumer<Response<GaeList<Bar>>> responseBars = response -> enqueueBars.accept(response.body()
                                                                                                         .getItems());
    private final Consumer<Throwable> errorBars = error -> enqueueBars.accept(Collections.emptyList());

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
