package com.bnorm.barkeep.ui.recipe.edit;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.bnorm.barkeep.data.api.model.Recipe;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(AndroidJUnit4.class)
public class EditRecipeActivityTest {

    @Rule public ActivityTestRule<EditRecipeActivity> activityRule = new ActivityTestRule<>(EditRecipeActivity.class,
                                                                                            false,
                                                                                            false);

    @Test
    public void createRecipe() {
        // given

        // when
        EditRecipeActivity activity = activityRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        // then
        assertThat(activity.name).hasText("");
        assertThat(activity.description).hasText("");
        assertThat(activity.directions).hasText("");
        assertThat(activity.adapter.getItems()).isEmpty();
    }

    @Test
    public void editRecipe() {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("Name");
        recipe.setDescription("Description");
        recipe.setDirections("Directions");

        // when
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.putExtra(EditRecipeActivity.RECIPE_TAG, recipe);
        EditRecipeActivity activity = activityRule.launchActivity(intent);

        // then
        assertThat(activity.name).hasText("Name");
        assertThat(activity.description).hasText("Description");
        assertThat(activity.directions).hasText("Directions");
    }
}

