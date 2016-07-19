package com.bnorm.barkeep.ui.book;

import java.io.IOException;
import java.util.Collections;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.endpoint.model.CollectionResponseBook;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookListPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock BookListView view;
    @Mock Endpoint endpoint;

    @NonNull
    private static BookListPresenter presenter(BookListView view, Endpoint endpoint) {
        return new BookListPresenter(view, endpoint, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void loadBooks_error() throws Exception {
        // given
        BookListPresenter presenter = presenter(view, endpoint);
        Endpoint.ListBooks listBooks = mock(Endpoint.ListBooks.class);
        when(endpoint.listBooks()).thenReturn(listBooks);
        when(listBooks.execute()).thenThrow(new IOException());

        // when
        presenter.loadBooks();

        // then
        verify(view).onBooks(Collections.emptyList());
    }

    @Test
    public void loadBooks_null() throws Exception {
        // given
        BookListPresenter presenter = presenter(view, endpoint);
        CollectionResponseBook response = new CollectionResponseBook();
        Endpoint.ListBooks listBooks = mock(Endpoint.ListBooks.class);
        when(endpoint.listBooks()).thenReturn(listBooks);
        when(listBooks.execute()).thenReturn(response);

        // when
        presenter.loadBooks();

        // then
        verify(view).onBooks(Collections.emptyList());
    }

    @Ignore
    @Test
    public void loadBooks_valid() throws Exception {
        // todo(bnorm) this test would be really smelly
    }
}