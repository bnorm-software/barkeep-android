package com.bnorm.barkeep.activity.book;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.recipe.ViewRecipeActivity;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridAdapter.RecipeViewHolder> {

    private static Integer[] mThumbIds = {
            R.drawable.cocktail_0, R.drawable.cocktail_1, R.drawable.cocktail_2, R.drawable.cocktail_3,
            R.drawable.cocktail_4, R.drawable.cocktail_5, R.drawable.cocktail_6, R.drawable.cocktail_7,
            R.drawable.cocktail_8,
            };

    private final Activity activity;
    private final List<Recipe> mItems;

    public RecipeGridAdapter(Activity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_recipe_grid, parent, false);
        int size = parent.getWidth() / 2;
        return new RecipeViewHolder(v, size);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mItems.get(position);
        holder.mText.setText(recipe.getName());
        RequestManager with = Glide.with(activity);
        DrawableTypeRequest<?> load;
        if (recipe.getPicture() != null) {
            load = with.load(recipe.getPicture());
        } else {
            load = with.load(mThumbIds[position % mThumbIds.length]);
        }
        load.centerCrop().override(holder.size, holder.size).into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void set(List<Recipe> recipes) {
        mItems.clear();
        mItems.addAll(recipes);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recipe_image) ImageView mImage;
        @Bind(R.id.recipe_name) TextView mText;
        private final int size;

        private RecipeViewHolder(View v, int size) {
            super(v);
            ButterKnife.bind(this, v);

            this.size = size;
            this.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ViewRecipeActivity.class);
                    intent.putExtra(ViewRecipeActivity.RECIPE_TAG, mItems.get(getLayoutPosition()));
                    activity.startActivity(intent);
                }
            });
        }
    }
}
