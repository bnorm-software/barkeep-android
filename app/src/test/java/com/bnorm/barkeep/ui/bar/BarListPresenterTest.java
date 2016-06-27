package com.bnorm.barkeep.ui.bar;

import java.io.IOException;
import java.util.Collections;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.endpoint.model.CollectionResponseBar;
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

public class BarListPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock BarListView view;
    @Mock Endpoint endpoint;

    @NonNull
    private static BarListPresenter presenter(BarListView view, Endpoint endpoint) {
        return new BarListPresenter(view, endpoint, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void loadBars_error() throws Exception {
        // given
        BarListPresenter presenter = presenter(view, endpoint);
        Endpoint.ListBars listBars = mock(Endpoint.ListBars.class);
        when(endpoint.listBars()).thenReturn(listBars);
        when(listBars.execute()).thenThrow(new IOException());

        // when
        presenter.loadBars();

        // then
        verify(view).onBars(Collections.emptyList());
    }

    @Test
    public void loadBars_null() throws Exception {
        // given
        BarListPresenter presenter = presenter(view, endpoint);
        CollectionResponseBar response = new CollectionResponseBar();
        Endpoint.ListBars listBars = mock(Endpoint.ListBars.class);
        when(endpoint.listBars()).thenReturn(listBars);
        when(listBars.execute()).thenReturn(response);

        // when
        presenter.loadBars();

        // then
        verify(view).onBars(Collections.emptyList());
    }

    @Ignore
    @Test
    public void loadBars_valid() throws Exception {
        // todo(bnorm) this test would be really smelly
    }
}