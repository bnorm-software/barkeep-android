package com.bnorm.barkeep.activity.recipe.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.recipe.ViewRecipeActivity;
import com.bnorm.barkeep.server.data.store.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Activity activity;
    private final List<Recipe> mItems;

    public RecipeAdapter(Activity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_recipe_name, parent, false);
        TextView name = (TextView) v.findViewById(R.id.recipe_search_name);
        return new RecipeViewHolder(v, name);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mName.setText(mItems.get(position).getName());
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
        private final TextView mName;

        private RecipeViewHolder(View v, TextView name) {
            super(v);
            this.mName = name;
            this.mName.setOnClickListener(new View.OnClickListener() {
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
