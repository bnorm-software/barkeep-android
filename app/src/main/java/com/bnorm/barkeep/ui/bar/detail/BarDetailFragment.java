package com.bnorm.barkeep.ui.bar.detail;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.trello.navi2.Event;
import com.trello.navi2.rx.RxNavi;

public class BarDetailFragment extends BaseFragment {
    private static final String BAR_TAG = "bar";

    // ===== View ===== //

    @BindView(R.id.ingredient_shelves) RecyclerView recyclerView;


    public static BarDetailFragment create(Bar bar) {
        Bundle args = new Bundle();
        args.putParcelable(BAR_TAG, bar);

        BarDetailFragment fragment = new BarDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_detail, container, false);
        ButterKnife.bind(this, view);

        Bar bar = Bundles.getParcelable(BAR_TAG,
                                        getArguments(),
                                        getActivity().getIntent().getExtras(),
                                        savedInstanceState);
        assert bar != null;
        RxNavi.observe(this, Event.SAVE_INSTANCE_STATE).subscribe(outState -> outState.putParcelable(BAR_TAG, bar));

        BarShelfAdapter adapter = new BarShelfAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        int num = 6;
        List<Ingredient> ingredients = bar.getIngredients();
        int subSize = ingredients.size() / num;
        List<BarShelf> temp = new ArrayList<>();
        for (int i = 0; i < num - 1; i++) {
            BarShelf shelf = new BarShelf(ingredients.subList(i * subSize, (i + 1) * subSize));
            temp.add(shelf);
        }
        adapter.set(temp);

        return view;
    }
}
