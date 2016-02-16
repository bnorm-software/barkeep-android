package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.book.RecipeGridAdapter;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;
import com.bnorm.barkeep.server.data.store.Bar;
import com.bnorm.barkeep.server.data.store.Ingredient;

public class IngredientShelvesFragment extends Fragment {

    private Bar mBar;

    public void setBar(Bar bar) {
        this.mBar = bar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null && mBar != null) {
            appBarLayout.setTitle(mBar.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingredient_shelves, container, false);

        RecyclerView shelves = (RecyclerView) root.findViewById(R.id.ingredient_shelves);
        LinearLayoutManager manager = new WrappingLinearLayoutManager(root.getContext(),
                                                                      LinearLayoutManager.VERTICAL,
                                                                      false);
        shelves.setLayoutManager(manager);

        IngredientShelfAdapter adapter = new IngredientShelfAdapter(getActivity());
        shelves.setAdapter(adapter);
        if (mBar != null) {
            int num = 6;
            List<Ingredient> ingredients = mBar.getIngredients();
            int subSize = ingredients.size() / num;
            List<BarShelf> temp = new ArrayList<>();
            for (int i = 0; i < num - 1; i++) {
                BarShelf shelf = new BarShelf(ingredients.subList(i * subSize, (i + 1) * subSize));
                temp.add(shelf);
            }
            adapter.set(temp);
        }

        return root;
    }
}
