package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
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
import com.bnorm.barkeep.databinding.ItemBarBinding;
import com.bnorm.barkeep.ui.MainActivity;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BarListFragment extends BaseFragment {

    @BindView(R.id.bar_list) RecyclerView recyclerView;
    @Nullable @BindView(R.id.bar_detail_container) FrameLayout detailContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_list, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Bars");
        }

        BarAdapter adapter = new BarAdapter();
        recyclerView.setAdapter(adapter);

        Observable.<List<Bar>>fromCallable(() -> {
            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar> bars;
            bars = barkeep().component().endpoint().listBars().execute().getItems();
            if (bars != null) {
                List<Bar> items = new ArrayList<>();
                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar bar : bars) {
                    items.add(new Bar(bar));
                }
                return items;
            } else {
                return Collections.emptyList();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
            adapter.set(result);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        // todo should this only be available within a book?
        EditRecipeActivity.launch(getContext());
    }

    public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

        private final List<Bar> items;

        public BarAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_bar, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.binding.setBar(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void set(List<Bar> bars) {
            items.clear();
            items.addAll(bars);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ItemBarBinding binding;

            public ViewHolder(View view) {
                super(view);
                binding = ItemBarBinding.bind(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (detailContainer != null) {
                            IngredientShelvesFragment fragment = new IngredientShelvesFragment();
                            fragment.setBar(binding.getBar());
                            getFragmentManager().beginTransaction().replace(detailContainer.getId(), fragment).commit();
                        } else {
                            Context context = v.getContext();
                            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
                            //                                                               R.anim.slide_in_from_right,
                            //                                                               R.anim.slide_out_to_right);
                            Intent intent = new Intent(context, BarDetailActivity.class);
                            intent.putExtra(BarDetailActivity.BAR_TAG, binding.getBar());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        }
    }
}
