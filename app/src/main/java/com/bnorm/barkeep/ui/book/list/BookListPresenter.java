package com.bnorm.barkeep.ui.book.list;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.GaeList;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import retrofit2.Response;
import rx.Scheduler;
import rx.functions.Action1;

@AppScope
public class BookListPresenter extends AbstractPresenter<BookListView> {

    private final Action1<List<Book>> enqueueBooks = enqueue(BookListView::onBooks);
    private final Action1<Response<GaeList<Book>>> responseBooks = response -> enqueueBooks.call(response.body()
                                                                                                         .getItems());
    private final Action1<Throwable> errorBooks = error -> enqueueBooks.call(Collections.emptyList());

    private final BarkeepService service;

    @Inject
    BookListPresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
    }

    public void loadBooks() {
        service.getBooks().subscribe(responseBooks, errorBooks);
    }
}
