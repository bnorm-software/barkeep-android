package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.databinding.ItemIngredientBinding;
import com.bnorm.barkeep.server.data.store.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.RecipeViewHolder> {

    private final List<Ingredient> ingredients;

    public IngredientAdapter() {
        this.ingredients = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.binding.setIngredient(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void set(List<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        private RecipeViewHolder(View v) {
            super(v);
            binding = ItemIngredientBinding.bind(v);
        }
    }
}
