package com.bnorm.barkeep.ui.recipe;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Amount;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.lib.BindingAdapters;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;

public class ViewRecipeActivity extends BaseActivity {

    private static final String RECIPE_TAG = ViewRecipeActivity.class.getName() + ".recipe";

    // ===== Model ===== //

    private Recipe recipe;


    // ===== View ===== //

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recipe_image) ImageView recipeImage;
    @BindView(R.id.recipe_view_components) LinearLayout recipeViewComponents;

    @Inject ViewContainer viewContainer;

    public static void launch(Context source, Recipe recipe) {
        Intent intent = new Intent(source, ViewRecipeActivity.class);
        intent.putExtra(RECIPE_TAG, recipe);
        source.startActivity(intent);
    }

    public static void launch(Context source, Recipe recipe, Bundle bundle) {
        Intent intent = new Intent(source, ViewRecipeActivity.class);
        intent.putExtra(RECIPE_TAG, recipe);
        source.startActivity(intent, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barkeep().component().plus(new ViewRecipeViewModule()).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        View view = getLayoutInflater().inflate(R.layout.activity_view_recipe, container, true);
        ButterKnife.bind(this, view);

        // ===== Pull intent extras ===== //
        Bundle bundle = getIntent().getExtras();
        recipe = bundle.getParcelable(RECIPE_TAG);
        assert recipe != null;


        // ===== Configure toolbar ===== //
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipe.getName());
        }


        // ===== Load recipe information ===== //
        BindingAdapters.loadImage(recipeImage, recipe.getPicture());

        for (Component component : recipe.getComponents()) {
            StringBuilder builder = new StringBuilder();
            Amount amount = component.getAmount();
            if (amount.getRecommended() != null) {
                builder.append(amount.getRecommended());
            } else {
                builder.append(amount.getMin());
                builder.append(" to ");
                builder.append(amount.getMax());
            }
            builder.append(" ");
            builder.append(component.getUnit());
            builder.append(" of ");

            boolean first = true;
            for (Ingredient ingredient : component.getIngredients()) {
                if (!first) {
                    builder.append(" or ");
                }
                builder.append(ingredient.getName());
                first = false;
            }

            // todo RecycleView
            TextView inflate = (TextView) getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            inflate.setText(builder.toString());
            recipeViewComponents.addView(inflate);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        EditRecipeActivity.launch(this, recipe);
    }
}
