package com.bnorm.barkeep.activity.bar;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.Retained;
import com.bnorm.barkeep.server.data.store.Bar;
import com.bnorm.barkeep.server.data.store.Ingredient;

public class BarDetailFragment extends Fragment {

    private Bar mBar;

    public void setBook(Bar bar) {
        this.mBar = bar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBar = Retained.retain(this, "bar", mBar);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mBar.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bar_detail, container, false);

        if (mBar != null) {
            StringBuilder builder = new StringBuilder();
            if (mBar.getIngredients() != null) {
                for (Ingredient ingredient : mBar.getIngredients()) {
                    builder.append(ingredient.getName()).append('\n');
                }
            }
            ((TextView) rootView.findViewById(R.id.book_detail)).setText(builder.toString());
        }

        return rootView;
    }
}
