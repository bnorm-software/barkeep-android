package com.bnorm.barkeep.ui.book.edit;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;

import javax.inject.Inject;

import rx.Scheduler;

@ActivityScope
public class EditBookPresenter {

    private final EditBookView view;
    private final BarkeepService service;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public EditBookPresenter(EditBookView view,
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

    public void save(Book book) {
        service.getBook(book.getName())
               .flatMap(response -> response.isSuccessful() ? service.updateBook(book.getName(), book)
                                                            : service.createBook(book))
               .subscribeOn(apiScheduler)
               .observeOn(uiScheduler)
               .subscribe(response -> {
                    view.onBookSaved(book);
                    view.onClose();
               });
    }
}
