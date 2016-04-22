package com.bnorm.barkeep.activity.book;

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
import com.bnorm.barkeep.databinding.ItemRecipeGridBinding;
import com.bnorm.barkeep.server.data.store.Recipe;

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridAdapter.RecipeViewHolder> {

    private final Activity activity;
    private final List<Recipe> mItems;

    public RecipeGridAdapter(Activity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_recipe_grid, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.binding.setRecipe(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void set(List<Recipe> recipes) {
        mItems.clear();
        mItems.addAll(recipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeGridBinding binding;

        private RecipeViewHolder(View v) {
            super(v);
            binding = ItemRecipeGridBinding.bind(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewRecipeActivity.class);
                    intent.putExtra(ViewRecipeActivity.RECIPE_TAG, mItems.get(getLayoutPosition()));
                    activity.startActivity(intent);
                }
            });
        }
    }
}
