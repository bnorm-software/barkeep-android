package com.bnorm.barkeep.ui.bar;

import java.util.List;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.ui.MainActivity;
import com.bnorm.barkeep.ui.bar.edit.EditBarActivity;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BarListFragment extends BaseFragment implements BarListView {

    // ===== View ====== //

    @BindView(R.id.bar_list) RecyclerView recyclerView;
    @Nullable @BindView(R.id.bar_detail_container) FrameLayout detailContainer;

    // ===== Presenter ===== //

    @Inject BarListPresenter presenter;
    @Inject BarAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        barkeep().component().plus(new BarListViewModule(this)).inject(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_list, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Bars");
        }

        // todo(bnorm) inject
        adapter = new BarAdapter(this);
        recyclerView.setAdapter(adapter);

        // todo(bnorm) inject
        presenter = new BarListPresenter(this,
                                         barkeep().component().service(),
                                         Schedulers.io(),
                                         AndroidSchedulers.mainThread());
        presenter.loadBars();
        return view;
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        EditBarActivity.launch(getContext());
    }

    @Override
    public void onBars(List<Bar> bars) {
        adapter.set(bars);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBarDetail(Bar bar) {
        if (detailContainer != null) {
            BarDetailFragment fragment = new BarDetailFragment();
            fragment.setBar(bar);
            getFragmentManager().beginTransaction().replace(detailContainer.getId(), fragment).commit();
        } else {
            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
            //                                                               R.anim.slide_in_from_right,
            //                                                               R.anim.slide_out_to_right);
            Intent intent = new Intent(getContext(), BarDetailActivity.class);
            intent.putExtra(BarDetailActivity.BAR_TAG, bar);
            startActivity(intent);
        }
    }
}
