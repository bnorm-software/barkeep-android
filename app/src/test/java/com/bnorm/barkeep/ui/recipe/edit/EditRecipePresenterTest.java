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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditRecipePresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock EditRecipeView view;
    @Mock Endpoint endpoint;

    @NonNull
    private EditRecipePresenter presenter(Recipe recipe) {
        return new EditRecipePresenter(view, endpoint, Schedulers.immediate(), Schedulers.immediate(), recipe);
    }

    @Test
    public void cancel() {
        // given
        EditRecipePresenter presenter = presenter(null);

        // when
        presenter.cancel();

        // then
        verify(view).onClose();
    }

    @Test
    public void validate() {
        // given
        EditRecipePresenter presenter = presenter(null);

        // when
        boolean validate = presenter.validate();

        // then
        assertThat(validate).named("validate").isTrue();
    }

    @Test
    public void save_valid_new() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setNameWords(new ArrayList<>());
        EditRecipePresenter presenter = presenter(recipe);
        when(view.getName()).thenReturn("name");
        when(view.getComponents()).thenReturn(new ArrayList<>());
        when(endpoint.getRecipe(any())).thenThrow(new IOException());
        when(endpoint.insertRecipe(any())).thenReturn(mock(Endpoint.InsertRecipe.class));

        // when
        presenter.save();

        // then
        verify(endpoint).getRecipe("name");
        verify(endpoint).insertRecipe(any());
        verify(view).onRecipeSaved(recipe);
        verify(view).onClose();
    }

    @Test
    public void save_valid_update() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setNameWords(new ArrayList<>());
        EditRecipePresenter presenter = presenter(recipe);
        when(view.getName()).thenReturn("name");
        when(view.getComponents()).thenReturn(new ArrayList<>());
        when(endpoint.getRecipe(any())).thenReturn(mock(Endpoint.GetRecipe.class));
        when(endpoint.updateRecipe(any(), any())).thenReturn(mock(Endpoint.UpdateRecipe.class));

        // when
        presenter.save();

        // then
        verify(endpoint).getRecipe("name");
        verify(endpoint).updateRecipe(eq("name"), any());
        verify(view).onRecipeSaved(recipe);
        verify(view).onClose();
    }

    @Test
    public void save_invalid() throws IOException {
        // given
        EditRecipePresenter presenter = spy(presenter(null));
        when(presenter.validate()).thenReturn(false);

        // when
        presenter.save();

        // then
        verify(presenter).validate();
    }

    @Test
    public void addComponent() {
        // given
        EditRecipePresenter presenter = presenter(null);

        // when
        presenter.addComponent();

        // then
        verify(view).onComponentDialog(eq(null), any(), eq("Cancel"));
    }

    @Test
    public void updateRecipe() {
        // given
        Recipe recipe = new Recipe();
        EditRecipePresenter presenter = presenter(recipe);
        when(view.getName()).thenReturn("Name");
        when(view.getDescription()).thenReturn("Description");
        when(view.getDirections()).thenReturn("Directions");
        when(view.getComponents()).thenReturn(null);

        // when
        presenter.updateRecipe();

        // then
        assertThat(recipe.getName()).named("recipe name").isEqualTo("Name");
        assertThat(recipe.getDescription()).named("recipe description").isEqualTo("Description");
        assertThat(recipe.getDirections()).named("recipe directions").isEqualTo("Directions");
        assertThat(recipe.getComponents()).named("recipe components").isEqualTo(null);
    }

    @Test
    public void recipe() {
        // given
        Recipe expected = new Recipe();
        EditRecipePresenter presenter = presenter(expected);

        // when
        Recipe actual = presenter.recipe();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}