package com.bnorm.barkeep.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Amount;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.databinding.ActivityViewRecipeBinding;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;

public class ViewRecipeActivity extends BaseActivity {

    private static final String RECIPE_TAG = ViewRecipeActivity.class.getName() + ".recipe";

    // ===== Model ===== //

    private ActivityViewRecipeBinding binding;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_recipe);


        // ===== Pull intent extras ===== //
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            binding.setRecipe(bundle.getParcelable(RECIPE_TAG));
        }


        // ===== Configure toolbar ===== //
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // ===== Configure floating button ===== //
        // todo what should be done with this floating action button?
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditRecipeActivity.launch(ViewRecipeActivity.this, binding.getRecipe());
            }
        });


        // ===== Load recipe information ===== //
        for (Component component : binding.getRecipe().getComponents()) {
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
            binding.content.recipeViewComponents.addView(inflate);
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
}