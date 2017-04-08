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
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

@AppScope
public class BookListPresenter extends AbstractPresenter<BookListView> {

    private final Consumer<List<Book>> enqueueBooks = enqueue(BookListView::onBooks);
    private final Consumer<Response<GaeList<Book>>> responseBooks = response -> enqueueBooks.accept(response.body()
                                                                                                            .getItems());
    private final Consumer<Throwable> errorBooks = error -> enqueueBooks.accept(Collections.emptyList());

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
