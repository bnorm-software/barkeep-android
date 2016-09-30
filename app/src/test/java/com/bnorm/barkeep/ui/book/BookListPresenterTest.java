package com.bnorm.barkeep.ui.book;

import android.support.annotation.NonNull;

import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.GaeList;

import com.bnorm.barkeep.ui.book.list.BookListPresenter;
import com.bnorm.barkeep.ui.book.list.BookListView;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Response;
import rx.Single;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookListPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock BookListView view;
    @Mock BarkeepService service;

    @NonNull
    private static BookListPresenter presenter(BookListView view, BarkeepService service) {
        BookListPresenter presenter = new BookListPresenter(service, Schedulers.immediate());
        presenter.attach(view);
        return presenter;
    }

    @Test
    public void loadBooks_error() throws Exception {
        // given
        BookListPresenter presenter = presenter(view, service);
        when(service.getBooks()).thenReturn(Single.error(new IOException()));

        // when
        presenter.loadBooks();

        // then
        verify(view).onBooks(Collections.emptyList());
    }

    @Test
    public void loadBooks_empty() throws Exception {
        // given
        BookListPresenter presenter = presenter(view, service);
        when(service.getBooks()).thenReturn(Single.just(Response.success(new GaeList<>())));

        // when
        presenter.loadBooks();

        // then
        verify(view).onBooks(Collections.emptyList());
    }

    @Test
    public void loadBooks_valid() throws Exception {
        // given
        BookListPresenter presenter = presenter(view, service);
        GaeList<Book> items = new GaeList<>(Arrays.asList(new Book(), new Book(), new Book()));
        when(service.getBooks()).thenReturn(Single.just(Response.success(items)));

        // when
        presenter.loadBooks();

        // then
        verify(view).onBooks(items.getItems());
    }
}