package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;

public class IngredientShelfAdapter extends RecyclerView.Adapter<IngredientShelfAdapter.RecipeViewHolder> {

    private final Activity activity;
    private final List<BarShelf> mItems;

    public IngredientShelfAdapter(Activity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_ingredient_shelf, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        BarShelf shelf = mItems.get(position);
        holder.mTitle.setText("SHELF!");
        holder.adapter.set(shelf.getIngredients());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void set(List<BarShelf> recipes) {
        mItems.clear();
        mItems.addAll(recipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_title) TextView mTitle;
        @BindView(R.id.ingredient_shelf_list) RecyclerView mIngredients;
        private final IngredientAdapter adapter;

        private RecipeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);


            adapter = new IngredientAdapter(activity);
            mIngredients.setAdapter(adapter);
            mIngredients.setLayoutManager(new WrappingLinearLayoutManager(v.getContext(),
                                                                          LinearLayoutManager.HORIZONTAL,
                                                                          false));
        }
    }
}
