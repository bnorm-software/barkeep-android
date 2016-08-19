package com.bnorm.barkeep.ui.book.edit;

import javax.inject.Inject;

import com.bnorm.barkeep.AppScope;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.ui.UiScheduler;
import com.bnorm.barkeep.ui.base.AbstractPresenter;
import retrofit2.Response;
import rx.Scheduler;
import rx.functions.Action1;

@AppScope
public class EditBookPresenter extends AbstractPresenter<EditBookView> {

    private final Action1<Book> enqueueBookSaved = enqueue(EditBookView::onBookSaved);
    private final Action1<Response<Book>> responseBookSaved = response -> enqueueBookSaved.call(response.body());

    private final BarkeepService service;

    @Inject
    EditBookPresenter(BarkeepService service, @UiScheduler Scheduler uiScheduler) {
        super(uiScheduler);
        this.service = service;
    }

    public void save(Book book) {
        validate(book);

        service.getBook(book.getName())
               .flatMap(response -> response.isSuccessful() ? service.updateBook(book.getName(), book)
                                                            : service.createBook(book))
               .subscribe(responseBookSaved);
    }

    private void validate(Book book) {
        if (book == null) throw new NullPointerException("Book is null");
        if (book.getName() == null) throw new NullPointerException("Book name is null");
        if (book.getRecipes() == null) throw new NullPointerException("Book recipes is null");
    }
}
