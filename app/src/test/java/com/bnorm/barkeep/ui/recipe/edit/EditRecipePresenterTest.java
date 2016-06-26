package com.bnorm.barkeep.ui.recipe.edit;

import java.io.IOException;
import java.util.ArrayList;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditRecipePresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock EditRecipeView view;
    @Mock Endpoint endpoint;

    @NonNull
    private static EditRecipePresenter presenter(EditRecipeView view, Endpoint endpoint) {
        return new EditRecipePresenter(view, endpoint, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void cancel() {
        // given
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        presenter.cancel();

        // then
        verify(view).onClose();
    }

    @Test
    public void save_null() {
        // given
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        boolean result = presenter.save(null);

        // then
        assertThat(result).named("save result").isFalse();
    }

    @Test
    public void save_nullName() {
        // given
        Recipe recipe = new Recipe();
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isFalse();
    }

    @Test
    public void save_nullNameWords() {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isFalse();
    }

    @Test
    public void save_nullComponents() {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isFalse();
    }

    @Test
    public void save_good() {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isTrue();
    }

    @Test
    public void save_valid_new() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);
        when(endpoint.getRecipe(any())).thenThrow(new IOException());
        when(endpoint.insertRecipe(any())).thenReturn(mock(Endpoint.InsertRecipe.class));

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isTrue();
        verify(endpoint).getRecipe("name");
        verify(endpoint).insertRecipe(any());
        verify(view).onRecipeSaved(recipe);
        verify(view).onClose();
    }

    @Test
    public void save_valid_update() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, endpoint);
        when(endpoint.getRecipe(any())).thenReturn(mock(Endpoint.GetRecipe.class));
        when(endpoint.updateRecipe(any(), any())).thenReturn(mock(Endpoint.UpdateRecipe.class));

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isTrue();
        verify(endpoint).getRecipe("name");
        verify(endpoint).updateRecipe(eq("name"), any());
        verify(view).onRecipeSaved(recipe);
        verify(view).onClose();
    }

    @Test
    public void addComponent() {
        // given
        EditRecipePresenter presenter = presenter(view, endpoint);

        // when
        presenter.addComponent();

        // then
        verify(view).onEditComponent(eq(null), any(), eq("Cancel"));
    }
}