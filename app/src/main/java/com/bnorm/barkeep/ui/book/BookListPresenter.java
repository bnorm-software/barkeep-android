package com.bnorm.barkeep.ui.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.data.api.ApiScheduler;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.ui.ActivityScope;
import com.bnorm.barkeep.ui.UiScheduler;
import rx.Observable;
import rx.Scheduler;

@ActivityScope
public class BookListPresenter {

    private final BookListView view;
    private final Endpoint endpoint;
    private final Scheduler apiScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public BookListPresenter(BookListView view, Endpoint endpoint, @ApiScheduler Scheduler apiScheduler,
                             @UiScheduler Scheduler uiScheduler) {
        this.view = view;
        this.endpoint = endpoint;
        this.apiScheduler = apiScheduler;
        this.uiScheduler = uiScheduler;
    }

    public void loadBooks() {
        Observable.fromCallable(() -> {
            try {
                List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book> books;
                books = endpoint.listBooks().execute().getItems();
                if (books != null) {
                    List<Book> items = new ArrayList<>();
                    for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book book : books) {
                        items.add(new Book(book));
                    }
                    return items;
                } else {
                    return Collections.<Book>emptyList();
                }
            } catch (IOException ignore) {
                return Collections.<Book>emptyList();
            }
        }).subscribeOn(apiScheduler).observeOn(uiScheduler).subscribe(result -> {
            view.onBooks(result);
        });
    }
}
