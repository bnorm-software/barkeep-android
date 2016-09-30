package com.bnorm.barkeep.ui.bar.list;

import java.util.List;
import javax.inject.Inject;

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
import com.bnorm.barkeep.ui.bar.detail.BarDetailActivity;
import com.bnorm.barkeep.ui.bar.detail.BarDetailFragment;
import com.bnorm.barkeep.ui.bar.edit.EditBarActivity;
import com.bnorm.barkeep.ui.base.BaseFragment;

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

        recyclerView.setAdapter(adapter);
        presenter.loadBars();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    public void onStop() {
        presenter.detach();
        super.onStop();
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
            BarDetailFragment fragment = BarDetailFragment.create(bar);
            getFragmentManager().beginTransaction().replace(detailContainer.getId(), fragment).commit();
        } else {
            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
            //                                                               R.anim.slide_in_from_right,
            //                                                               R.anim.slide_out_to_right);
            BarDetailActivity.launch(getContext(), bar);
        }
    }
}
