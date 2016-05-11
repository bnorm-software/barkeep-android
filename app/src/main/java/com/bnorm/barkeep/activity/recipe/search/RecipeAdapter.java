package com.bnorm.barkeep.activity.recipe.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.recipe.ViewRecipeActivity;
import com.bnorm.barkeep.databinding.ItemRecipeNameBinding;
import com.bnorm.barkeep.server.data.store.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Activity activity;
    private final List<Recipe> recipes;

    public RecipeAdapter(Activity activity) {
        this.activity = activity;
        this.recipes = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_recipe_name, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.binding.setRecipe(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void set(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeNameBinding binding;

        private RecipeViewHolder(View v) {
            super(v);
            binding = ItemRecipeNameBinding.bind(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewRecipeActivity.class);
                    intent.putExtra(ViewRecipeActivity.RECIPE_TAG, recipes.get(getLayoutPosition()));
                    activity.startActivity(intent);
                }
            });
        }
    }
}
