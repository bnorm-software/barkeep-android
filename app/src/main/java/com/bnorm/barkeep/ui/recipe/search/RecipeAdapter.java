package com.bnorm.barkeep.ui.recipe.search;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final SearchRecipeView activity;
    private final List<Recipe> items;

    @Inject
    public RecipeAdapter(SearchRecipeView activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeViewHolder holder = new RecipeViewHolder(parent);
        holder.itemView.setOnClickListener(v -> onRecipeClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    private void onRecipeClick(int position) {
        activity.onRecipeView(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void set(List<Recipe> recipes) {
        this.items.clear();
        this.items.addAll(recipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_search_name) TextView recipeSearchName;

        private RecipeViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_name, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            recipeSearchName.setText(recipe.getName());
        }
    }
}
