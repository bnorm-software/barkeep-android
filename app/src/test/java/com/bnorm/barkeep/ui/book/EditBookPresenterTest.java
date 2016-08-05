package com.bnorm.barkeep.ui.book;

import android.support.annotation.NonNull;

import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.ui.book.edit.EditBookPresenter;
import com.bnorm.barkeep.ui.book.edit.EditBookView;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Single;
import rx.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditBookPresenterTest {

    private static final ResponseBody EMPTY_BODY = ResponseBody.create(MediaType.parse("application/json"), "");
    private static final Response<Book> ERROR_404 = Response.error(404, EMPTY_BODY);

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock EditBookView view;
    @Mock BarkeepService service;

    @NonNull
    private static EditBookPresenter presenter(EditBookView view, BarkeepService service) {
        return new EditBookPresenter(view, service, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void cancel() {
        // given
        EditBookPresenter presenter = presenter(view, service);

        // when
        presenter.cancel();

        // then
        verify(view).onClose();
    }

    @Test
    public void save_null() {
        // given
        EditBookPresenter presenter = presenter(view, service);

        // when
        IllegalArgumentException exception = null;
        try {
            presenter.save(null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertThat(exception != null).named("save fails for null").isTrue();
    }

    @Test
    public void save_nullName() {
        // given
        Book book = new Book();
        book.setName(null);
        book.setRecipes(Collections.emptyList());
        EditBookPresenter presenter = presenter(view, service);

        // when
        IllegalArgumentException exception = null;
        try {
            presenter.save(book);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertThat(exception != null).named("save fails for nullName").isTrue();
    }

    @Test
    public void save_nullRecipes() {
        // given
        Book book = new Book();
        book.setName("name");
        book.setRecipes(null);
        EditBookPresenter presenter = presenter(view, service);

        // when
        IllegalArgumentException exception = null;
        try {
            presenter.save(book);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertThat(exception != null).named("save fails for nullRecipes").isTrue();
    }

    @Test
    public void save_valid_new() {
        // given
        Book book = new Book();
        book.setName("name");
        book.setRecipes(Collections.emptyList());
        EditBookPresenter presenter = presenter(view, service);
        when(service.getBook(any())).thenReturn(Single.just(ERROR_404));
        when(service.createBook(any())).thenReturn(Single.just(Response.success(null)));

        // when
        presenter.save(book);

        // then
        verify(service).getBook("name");
        verify(service).createBook(any());
        verify(view).onBookSaved(any());
        verify(view).onClose();
    }

    @Test
    public void save_valid_update() {
        // given
        Book book = new Book();
        book.setName("name");
        book.setRecipes(Collections.emptyList());
        EditBookPresenter presenter = presenter(view, service);
        when(service.getBook(any())).thenReturn(Single.just(Response.success(book)));
        when(service.updateBook(any(), any())).thenReturn(Single.just(Response.success(book)));

        // when
        presenter.save(book);

        // then
        verify(service).getBook("name");
        verify(service).updateBook(eq("name"), any());
        verify(view).onBookSaved(any());
        verify(view).onClose();
    }
}
