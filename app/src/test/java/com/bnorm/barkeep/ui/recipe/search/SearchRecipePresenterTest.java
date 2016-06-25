package com.bnorm.barkeep.ui.recipe.search;

import java.io.IOException;
import java.util.Collections;

import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.bnorm.barkeep.server.data.store.v1.endpoint.model.CollectionResponseRecipe;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPluginsRule;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPluginsRule;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchRecipePresenterTest {
    @Rule public RxJavaPluginsRule rxJavaPluginsRule = new RxJavaPluginsRule(new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }
    });
    @Rule public RxAndroidPluginsRule rxAndroidPluginsRule = new RxAndroidPluginsRule(new RxAndroidSchedulersHook() {
        @Override
        public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    });
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock SearchRecipeView view;
    @Mock Endpoint endpoint;

    @Test
    public void submit_networkFailure() throws Exception {
        // given
        SearchRecipePresenter presenter = new SearchRecipePresenter(view, endpoint);
        when(endpoint.listRecipes()).thenThrow(new IOException());

        // when
        presenter.submit("");

        // then
        verify(view).onSearchResults(Collections.emptyList());
    }

    @Test
    public void submit() throws Exception {
        // given
        SearchRecipePresenter presenter = new SearchRecipePresenter(view, endpoint);

        // todo(bnorm) eww...  smelly...
        Endpoint.ListRecipes listRecipes = mock(Endpoint.ListRecipes.class);
        CollectionResponseRecipe responseRecipe = new CollectionResponseRecipe();
        responseRecipe.setItems(null);
        when(endpoint.listRecipes()).thenReturn(listRecipes);
        when(listRecipes.setName(any())).thenReturn(listRecipes);
        when(listRecipes.execute()).thenReturn(responseRecipe);

        // when
        presenter.submit("");

        // then
        verify(view).onSearchResults(Collections.emptyList());
    }
}