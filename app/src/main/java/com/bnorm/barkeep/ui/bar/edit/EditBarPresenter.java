package com.bnorm.barkeep.ui.bar.edit;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;

import javax.inject.Inject;

import rx.Scheduler;

@ActivityScope
public class EditBarPresenter {

    private final EditBarView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public EditBarPresenter(EditBarView view,
                            BarkeepService service,
                            @ApiScheduler Scheduler apiScheduler,
                            @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.service = service;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void cancel() {
        view.onClose();
    }

    public void save(Bar bar) {
        service.getBar(bar.getName())
               .flatMap(response -> response.isSuccessful() ? service.updateBar(bar.getName(), bar)
                                                            : service.createBar(bar))
               .subscribeOn(apiScheduler)
               .observeOn(uiScheduler)
               .subscribe(response -> {
                    view.onBarSaved(bar);
                    view.onClose();
               });
    }
}
