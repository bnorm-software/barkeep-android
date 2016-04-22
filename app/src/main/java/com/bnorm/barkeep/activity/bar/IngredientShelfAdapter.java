package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.databinding.ItemIngredientShelfBinding;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;

public class IngredientShelfAdapter extends RecyclerView.Adapter<IngredientShelfAdapter.RecipeViewHolder> {

    private final List<BarShelf> shelves;

    public IngredientShelfAdapter() {
        this.shelves = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_ingredient_shelf, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        BarShelf shelf = shelves.get(position);
        holder.adapter.set(shelf.getIngredients());
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

        private RecipeViewHolder(View v) {
            super(v);
            binding = ItemIngredientShelfBinding.bind(v);
            adapter = new IngredientAdapter();

            binding.ingredientShelfList.setAdapter(adapter);
            binding.ingredientShelfList.setLayoutManager(new WrappingLinearLayoutManager(v.getContext(),
                                                                                         LinearLayoutManager.HORIZONTAL,
                                                                                         false));
        }
    }
}
