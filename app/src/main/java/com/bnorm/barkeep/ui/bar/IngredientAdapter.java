package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.lib.BindingAdapters;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {

    private final List<Ingredient> items = new ArrayList<>();

    @Override
    public IngredientAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setAll(List<Ingredient> all) {
        this.items.clear();
        this.items.addAll(all);
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_title) TextView recipeTitle;
        @BindView(R.id.ingredient_image) ImageView recipeImage;

        public Holder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false));
            ButterKnife.bind(this, itemView);
            BindingAdapters.loadImage(recipeImage,
                                      "http://2.bp.blogspot.com/-PIQcxG5KkiU/To8duH-jYDI/AAAAAAAACEk/OtbVHe8EjmI/s200/jameson.jpg");
        }

        public void bind(Ingredient ingredient) {
            recipeTitle.setText(ingredient.getName());
        }
    }
}
