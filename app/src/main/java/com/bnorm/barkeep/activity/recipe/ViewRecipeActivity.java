package com.bnorm.barkeep.activity.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivity;
import com.bnorm.barkeep.server.data.store.Amount;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Ingredient;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.task.RecipeAsyncTask;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class ViewRecipeActivity extends BaseActivity {

    public static final String RECIPE_TAG = ViewRecipeActivity.class.getName() + ".recipe";

    // ===== Model ===== //

    private Recipe mRecipe;


    // ===== View ===== //

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.toolbar_image) ImageView mImageView;
    @Bind(R.id.fab) FloatingActionButton mFab;
    @Bind(R.id.recipe_view_description) TextView mDescription;
    @Bind(R.id.recipe_view_directions) TextView mDirections;
    @Bind(R.id.recipe_view_components) LinearLayout mComponents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        // ===== Pull intent extras ===== //
        if (bundle != null) {
            mRecipe = bundle.getParcelable(RECIPE_TAG);
        }
        if (mRecipe == null) {
            mRecipe = new Recipe();
        }


        // ===== Configure toolbar ===== //
        mToolbar.setTitle(mRecipe.getName());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // ===== Configure floating button ===== //
        // todo what should be done with this floating action button?
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecipeActivity.this, EditRecipeActivity.class);
                intent.putExtra(EditRecipeActivity.RECIPE_TAG, mRecipe);
                startActivity(intent);
            }
        });


        // ===== Load recipe information ===== //
        if (mRecipe == null) {
            RecipeAsyncTask task = new RecipeAsyncTask() {
                @Override
                protected void onPostExecute(Recipe recipe) {
                    mRecipe = recipe;
                    loadRecipe(recipe);
                }
            };
            task.execute(mRecipe.getName());
        } else {
            loadRecipe(mRecipe);
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

    private void loadRecipe(Recipe recipe) {
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

            // todo RecycleView?
            TextView inflate = (TextView) getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            inflate.setText(builder.toString());
            mComponents.addView(inflate);
        }

        mDescription.setText(recipe.getDescription());
        mDirections.setText(recipe.getDirections());

        RequestManager with = Glide.with(this);
        DrawableTypeRequest<?> load;
        if (recipe.getPicture() != null) {
            load = with.load(recipe.getPicture());
        } else {
            load = with.load(R.drawable.cocktail_image);
        }
        load.centerCrop().into(mImageView);
    }
}
