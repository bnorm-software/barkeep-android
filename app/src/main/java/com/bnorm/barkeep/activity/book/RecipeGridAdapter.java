package com.bnorm.barkeep.activity.book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bnorm.barkeep.activity.recipe.ViewRecipeActivity;
import com.bnorm.barkeep.databinding.ItemRecipeGridBinding;
import com.bnorm.barkeep.lib.ListBindingAdapter;
import com.bnorm.barkeep.server.data.store.Recipe;

public class RecipeGridAdapter extends ListBindingAdapter<Recipe, ItemRecipeGridBinding> {

    @Override
    public BindingViewHolder<ItemRecipeGridBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecipeGridBinding binding = ItemRecipeGridBinding.inflate(inflater, parent, false);
        binding.getRoot().setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, ViewRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_TAG, binding.getRecipe());
            context.startActivity(intent);
        });
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemRecipeGridBinding> holder, int position) {
        holder.getBinding().setRecipe(items.get(position));
    }
}
