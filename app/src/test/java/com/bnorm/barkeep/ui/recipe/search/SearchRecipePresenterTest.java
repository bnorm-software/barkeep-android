package com.bnorm.barkeep.ui.recipe.search;

import java.io.IOException;
import java.util.Collections;

import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.GaeList;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchRecipePresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock SearchRecipeView view;
    @Mock BarkeepService service;

    private static SearchRecipePresenter presenter(SearchRecipeView view, BarkeepService service) {
        SearchRecipePresenter presenter = new SearchRecipePresenter(service, Schedulers.trampoline());
        presenter.attach(view);
        return presenter;
    }

    @Test
    public void submit_networkFailure() throws Exception {
        // given
        SearchRecipePresenter presenter = presenter(view, service);
        when(service.getRecipes(any())).thenReturn(Single.error(new IOException()));

        // when
        presenter.submit("");

        // then
        verify(view).onSearchResults(Collections.emptyList());
    }

    @Test
    public void submit() throws Exception {
        // given
        SearchRecipePresenter presenter = presenter(view, service);
        when(service.getRecipes(any())).thenReturn(Single.just(Response.success(new GaeList<>())));

        // when
        presenter.submit("");

        // then
        verify(view).onSearchResults(Collections.emptyList());
    }
}