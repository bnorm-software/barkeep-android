package com.bnorm.barkeep.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
        }

        return view;
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        // todo should this only be available within a book?
        EditRecipeActivity.launch(getContext());
    }
}
