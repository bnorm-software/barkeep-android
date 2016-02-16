package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Ingredient;
import com.bumptech.glide.Glide;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.RecipeViewHolder> {

    private final Activity activity;
    private final List<Ingredient> mItems;

    public IngredientAdapter(Activity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Ingredient ingredient = mItems.get(position);
        holder.mText.setText(ingredient.getName());
        Glide.with(activity)
             .load("http://2.bp.blogspot.com/-PIQcxG5KkiU/To8duH-jYDI/AAAAAAAACEk/OtbVHe8EjmI/s200/jameson.jpg")
             .centerCrop()
//             .override(500, 500)
             .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void set(List<Ingredient> ingredients) {
        mItems.clear();
        mItems.addAll(ingredients);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ingredient_image) ImageView mImage;
        @Bind(R.id.ingredient_name) TextView mText;

        private RecipeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
