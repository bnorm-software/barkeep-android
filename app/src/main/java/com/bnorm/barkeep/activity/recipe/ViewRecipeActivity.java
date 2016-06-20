package com.bnorm.barkeep.activity.recipe;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivity;
import com.bnorm.barkeep.databinding.ActivityViewRecipeBinding;
import com.bnorm.barkeep.server.data.store.Amount;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Ingredient;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;

public class ViewRecipeActivity extends BaseActivity {

    public static final String RECIPE_TAG = ViewRecipeActivity.class.getName() + ".recipe";

    // ===== Model ===== //

    private ActivityViewRecipeBinding binding;


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
