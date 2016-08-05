package com.bnorm.barkeep.ui.bar;

import android.support.annotation.NonNull;

import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.ui.bar.edit.EditBarPresenter;
import com.bnorm.barkeep.ui.bar.edit.EditBarView;

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

public class EditBarPresenterTest {

    private static final ResponseBody EMPTY_BODY = ResponseBody.create(MediaType.parse("application/json"), "");
    private static final Response<Bar> ERROR_404 = Response.error(404, EMPTY_BODY);

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock EditBarView view;
    @Mock BarkeepService service;

    @NonNull
    private static EditBarPresenter presenter(EditBarView view, BarkeepService service) {
        return new EditBarPresenter(view, service, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void cancel() {
        // given
        EditBarPresenter presenter = presenter(view, service);

        // when
        presenter.cancel();

        // then
        verify(view).onClose();
    }

    @Test
    public void save_null() {
        // given
        EditBarPresenter presenter = presenter(view, service);

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
        Bar bar = new Bar();
        bar.setName(null);
        bar.setIngredients(Collections.emptyList());
        EditBarPresenter presenter = presenter(view, service);

        // when
        IllegalArgumentException exception = null;
        try {
            presenter.save(bar);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertThat(exception != null).named("save fails for nullName").isTrue();
    }

    @Test
    public void save_nullIngredients() {
        // given
        Bar bar = new Bar();
        bar.setName("name");
        bar.setIngredients(null);
        EditBarPresenter presenter = presenter(view, service);

        // when
        IllegalArgumentException exception = null;
        try {
            presenter.save(bar);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // then
        assertThat(exception != null).named("save fails for nullIngredients").isTrue();
    }

    @Test
    public void save_valid_new() {
        // given
        Bar bar = new Bar();
        bar.setName("name");
        bar.setIngredients(Collections.emptyList());
        EditBarPresenter presenter = presenter(view, service);
        when(service.getBar(any())).thenReturn(Single.just(ERROR_404));
        when(service.createBar(any())).thenReturn(Single.just(Response.success(null)));

        // when
        presenter.save(bar);

        // then
        verify(service).getBar("name");
        verify(service).createBar(any());
        verify(view).onBarSaved(any());
        verify(view).onClose();
    }

    @Test
    public void save_valid_update() {
        // given
        Bar bar = new Bar();
        bar.setName("name");
        bar.setIngredients(Collections.emptyList());
        EditBarPresenter presenter = presenter(view, service);
        when(service.getBar(any())).thenReturn(Single.just(Response.success(bar)));
        when(service.updateBar(any(), any())).thenReturn(Single.just(Response.success(bar)));

        // when
        presenter.save(bar);

        // then
        verify(service).getBar("name");
        verify(service).updateBar(eq("name"), any());
        verify(view).onBarSaved(any());
        verify(view).onClose();
    }
}
