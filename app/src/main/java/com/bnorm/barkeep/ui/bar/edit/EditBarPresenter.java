package com.bnorm.barkeep.ui.bar.edit;

import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

@AppScope
public class EditBarPresenter extends AbstractPresenter<EditBarView> {

    private final Consumer<Bar> enqueueBarSaved = enqueue(EditBarView::onBarSaved);
    private final Consumer<Response<Bar>> responseBarSaved = response -> enqueueBarSaved.accept(response.body());

    private final BarkeepService service;

    @Inject
    EditBarPresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
    }

    public void save(Bar bar) {
        validate(bar);

        service.getBar(bar.getName())
               .flatMap(response -> response.isSuccessful() ? service.updateBar(bar.getName(), bar)
                                                            : service.createBar(bar))
               .subscribe(responseBarSaved);
    }

    private void validate(Bar bar) {
        if (bar == null) throw new NullPointerException("Bar is null");
        if (bar.getName() == null) throw new NullPointerException("Bar name is null");
        if (bar.getIngredients() == null) throw new NullPointerException("Bar ingredients is null");
    }
}
