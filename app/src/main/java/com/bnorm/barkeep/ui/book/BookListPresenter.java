package com.bnorm.barkeep.ui.book;

import java.util.Collections;
import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Scheduler;

@ActivityScope
public class BookListPresenter {

    private final BookListView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public BookListPresenter(BookListView view, BarkeepService service, @ApiScheduler Scheduler apiScheduler,
                             @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.service = service;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void loadBooks() {
        service.getBooks().subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(response -> {
            view.onBooks(response.body().getItems());
        }, error -> {
            view.onBooks(Collections.emptyList());
        });
    }
}
