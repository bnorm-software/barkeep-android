package com.bnorm.barkeep.ui.recipe.search;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class SearchRecipeActivityTest {

    @Rule public ActivityTestRule<SearchRecipeActivity> activityRule = new ActivityTestRule<>(SearchRecipeActivity.class,
                                                                                              false,
                                                                                              false);

    @Test
    public void starts() {
        // given

        // when
        SearchRecipeActivity activity = activityRule.launchActivity(new Intent(Intent.ACTION_MAIN));

        // then
        assertThat(activity).isNotNull();
    }
}

