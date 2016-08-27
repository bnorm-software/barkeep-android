package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;

public class BarShelfAdapter extends RecyclerView.Adapter<BarShelfAdapter.RecipeViewHolder> {

    private final List<BarShelf> shelves;

    public BarShelfAdapter() {
        this.shelves = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(parent);
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
        @BindView(R.id.ingredient_shelf_list) RecyclerView ingredientShelfList;
        private final IngredientAdapter adapter;

        private RecipeViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_shelf, parent, false));
            this.adapter = new IngredientAdapter();

            ButterKnife.bind(this, itemView);
            ingredientShelfList.setAdapter(adapter);
            ingredientShelfList.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                                                                         LinearLayoutManager.HORIZONTAL,
                                                                         false));
        }
    }
}
