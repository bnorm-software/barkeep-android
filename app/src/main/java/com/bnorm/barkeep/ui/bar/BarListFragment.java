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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.databinding.ItemBarBinding;
import com.bnorm.barkeep.ui.MainActivity;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.edit.EditRecipeActivity;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
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

        private static final int RATIO = 1 + 1; // 1 to 1
        // 3 - 3/2 =

        // ratio: 1:1 = 2
        // 3 + X = 5 ; X=2 ; 3/1-1
        // 4 + X = 7 ; X=3 ; 4/1-1
        // 5 + X = 9 ; X=4 ; 5/1-1

        // ration 2:1 = 3
        // 3 + X = 4 ; X=1 ; 3/2-1
        // 4 + X = 5 ; X=1 ; 4/2-1
        // 5 + X = 7 ; X=2 ; 5/2-1

        private static final int NORMAL = 0;
        private static final int AD = 1;

        private final List<Bar> items;

        public BarAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == NORMAL) {
                View view = inflater.inflate(R.layout.item_bar, parent, false);
                holder = new NormalViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.item_ad, parent, false);
                holder = new AdViewHolder(view);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(items.get(toItemPosition(position)));
        }

        private int toItemPosition(int adapterPosition) {
            return adapterPosition - (adapterPosition / RATIO);
        }

        private int toAdapterPosition(int itemPosition) {
            return itemPosition + (itemPosition / RATIO);
        }

        @Override
        public int getItemCount() {
            int size = items.size();
            int end = size % (RATIO - 1) == 0 ? 1 : 0;
            return size + size / (RATIO - 1) - end;
        }

        @Override
        public int getItemViewType(int position) {
            return (position % RATIO) == (RATIO - 1) ? AD : NORMAL;
        }

        public void set(List<Bar> bars) {
            items.clear();
            items.addAll(bars);
        }

        public class NormalViewHolder extends ViewHolder {
            private final ItemBarBinding binding;

            public NormalViewHolder(View view) {
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

            @Override
            public void bind(Bar bar) {
                binding.setBar(bar);
            }
        }

        public class AdViewHolder extends ViewHolder {

            @BindView(R.id.ad_view) NativeContentAdView adView;
            @BindView(R.id.ad_text) TextView adText;

            public AdViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @Override
            public void bind(Bar bar) {
                AdLoader.Builder loaderBuilder = new AdLoader.Builder(getContext(), "/6499/example/native");
                PublisherAdRequest.Builder requestBuilder = new PublisherAdRequest.Builder();
                loaderBuilder.forContentAd(this::bind).build().loadAd(requestBuilder.build());
            }

            private void bind(NativeContentAd contentAd) {
                adText.setText(contentAd.getHeadline());
                adView.setHeadlineView(adText);

                adView.setNativeAd(contentAd);
            }
        }

        public abstract class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View view) {
                super(view);
            }

            public abstract void bind(Bar bar);
        }
    }
}
