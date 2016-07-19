package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.databinding.ItemIngredientShelfBinding;

public class BarShelfAdapter extends RecyclerView.Adapter<BarShelfAdapter.RecipeViewHolder> {

    private final List<BarShelf> shelves;

    public BarShelfAdapter() {
        this.shelves = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_ingredient_shelf, parent, false);
        ItemIngredientShelfBinding binding = ItemIngredientShelfBinding.bind(v);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        BarShelf shelf = shelves.get(position);
        holder.adapter.setAll(shelf.getIngredients());
    }

    @Override
    public int getItemCount() {
        return shelves.size();
    }

    public void set(List<BarShelf> shelves) {
        this.shelves.clear();
        this.shelves.addAll(shelves);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientShelfBinding binding;
        private final IngredientAdapter adapter;

        private RecipeViewHolder(ItemIngredientShelfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.adapter = new IngredientAdapter();

            RecyclerView ingredientShelfList = this.binding.ingredientShelfList;
            ingredientShelfList.setAdapter(adapter);
            ingredientShelfList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(),
                                                                         LinearLayoutManager.HORIZONTAL,
                                                                         false));
        }
    }
}
