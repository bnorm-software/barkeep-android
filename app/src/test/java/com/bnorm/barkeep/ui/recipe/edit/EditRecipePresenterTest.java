package com.bnorm.barkeep.ui.recipe.edit;

import java.io.IOException;
import java.util.ArrayList;

import android.support.annotation.NonNull;
import com.bnorm.barkeep.data.api.BarkeepService;
import com.bnorm.barkeep.data.api.model.Recipe;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditRecipePresenterTest {

    private static final ResponseBody EMPTY_BODY = ResponseBody.create(MediaType.parse("application/json"), "");
    private static final Response<Recipe> ERROR_404 = Response.error(404, EMPTY_BODY);

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock EditRecipeView view;
    @Mock BarkeepService service;

    @NonNull
    private static EditRecipePresenter presenter(EditRecipeView view, BarkeepService service) {
        return new EditRecipePresenter(view, service, Schedulers.immediate(), Schedulers.immediate());
    }

    @Test
    public void cancel() {
        // given
        EditRecipePresenter presenter = presenter(view, service);

        // when
        presenter.cancel();

        // then
        verify(view).onClose();
    }

    @Test
    public void save_null() {
        // given
        EditRecipePresenter presenter = presenter(view, service);

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
        EditRecipePresenter presenter = presenter(view, service);

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
        EditRecipePresenter presenter = presenter(view, service);

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
        recipe.setComponents(null);
        EditRecipePresenter presenter = presenter(view, service);

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isFalse();
    }

    @Test
    public void save_valid_new() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, service);
        when(service.getRecipe(any())).thenReturn(Single.just(ERROR_404));
        when(service.createRecipe(any())).thenReturn(Single.just(Response.success(null)));

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isTrue();
        verify(service).getRecipe("name");
        verify(service).createRecipe(any());
        verify(view).onRecipeSaved(any());
        verify(view).onClose();
    }

    @Test
    public void save_valid_update() throws IOException {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setNameWords(new ArrayList<>());
        recipe.setComponents(new ArrayList<>());
        EditRecipePresenter presenter = presenter(view, service);
        when(service.getRecipe(any())).thenReturn(Single.just(Response.success(recipe)));
        when(service.updateRecipe(any(), any())).thenReturn(Single.just(Response.success(recipe)));

        // when
        boolean result = presenter.save(recipe);

        // then
        assertThat(result).named("save result").isTrue();
        verify(service).getRecipe("name");
        verify(service).updateRecipe(eq("name"), any());
        verify(view).onRecipeSaved(any());
        verify(view).onClose();
    }

    @Test
    public void addComponent() {
        // given
        EditRecipePresenter presenter = presenter(view, service);

        // when
        presenter.addComponent();

        // then
        verify(view).onEditComponent(eq(null), any(), eq("Cancel"));
    }
}