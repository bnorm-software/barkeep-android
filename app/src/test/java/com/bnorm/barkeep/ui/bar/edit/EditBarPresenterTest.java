package com.bnorm.barkeep.ui.bar.edit;

import java.util.Collections;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.Response;
import rx.Single;
import rx.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
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
        EditBarPresenter presenter = new EditBarPresenter(service, Schedulers.immediate());
        presenter.attach(view);
        return presenter;
    }

    @Test
    public void save_null() {
        // given
        EditBarPresenter presenter = presenter(view, service);

        // when
        try {
            presenter.save(null);
            fail("Presenter should have thrown an exception on save");
        } catch (NullPointerException e) {
            // then
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }

    @Test
    public void save_nullName() {
        // given
        Bar bar = new Bar();
        bar.setName(null);
        bar.setIngredients(Collections.emptyList());
        EditBarPresenter presenter = presenter(view, service);

        // when
        try {
            presenter.save(bar);
            fail("Presenter should have thrown an exception on save");
        } catch (NullPointerException e) {
            // then
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }

    @Test
    public void save_nullIngredients() {
        // given
        Bar bar = new Bar();
        bar.setName("name");
        bar.setIngredients(null);
        EditBarPresenter presenter = presenter(view, service);

        // when
        try {
            presenter.save(bar);
            fail("Presenter should have thrown an exception on save");
        } catch (NullPointerException e) {
            // then
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
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
    }
}
