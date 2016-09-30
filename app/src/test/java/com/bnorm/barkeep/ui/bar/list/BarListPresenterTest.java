package com.bnorm.barkeep.ui.bar.list;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.data.api.model.GaeList;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.Response;
import rx.Single;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BarListPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock BarListView view;
    @Mock BarkeepService service;

    @NonNull
    private static BarListPresenter presenter(BarListView view, BarkeepService service) {
        BarListPresenter presenter = new BarListPresenter(service, Schedulers.immediate());
        presenter.attach(view);
        return presenter;
    }

    @Test
    public void loadBars_error() throws Exception {
        // given
        BarListPresenter presenter = presenter(view, service);
        when(service.getBars()).thenReturn(Single.error(new IOException()));

        // when
        presenter.loadBars();

        // then
        verify(view).onBars(Collections.emptyList());
    }

    @Test
    public void loadBars_empty() throws Exception {
        // given
        BarListPresenter presenter = presenter(view, service);
        when(service.getBars()).thenReturn(Single.just(Response.success(new GaeList<>())));

        // when
        presenter.loadBars();

        // then
        verify(view).onBars(Collections.emptyList());
    }

    @Test
    public void loadBars_valid() throws Exception {
        // given
        BarListPresenter presenter = presenter(view, service);
        GaeList<Bar> items = new GaeList<>(Arrays.asList(new Bar(), new Bar(), new Bar()));
        when(service.getBars()).thenReturn(Single.just(Response.success(items)));

        // when
        presenter.loadBars();

        // then
        verify(view).onBars(items.getItems());
    }
}