package com.bnorm.barkeep.ui.bar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.databinding.ItemIngredientBinding;
import com.bnorm.barkeep.lib.ListBindingAdapter;

public class IngredientAdapter extends ListBindingAdapter<Ingredient, ItemIngredientBinding> {

    @Override
    public BindingViewHolder<ItemIngredientBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BindingViewHolder<>(ItemIngredientBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemIngredientBinding> holder, int position) {
        holder.getBinding().setIngredient(items.get(position));
    }
}
