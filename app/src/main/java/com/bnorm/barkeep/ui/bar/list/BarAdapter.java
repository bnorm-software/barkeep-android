package com.bnorm.barkeep.ui.bar.list;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

    private final BarListView view;
    private final List<Bar> items;

    @Inject
    public BarAdapter(BarListView view) {
        this.view = view;
        this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(parent);
        holder.itemView.setOnClickListener(v -> onBarClick(holder.getAdapterPosition()));
        return holder;
    }

    private void onBarClick(int position) {
        view.onBarDetail(items.get(position));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
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
        @BindView(R.id.bar_title) TextView barTitle;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(Bar bar) {
            barTitle.setText(bar.getName());
        }
    }
}
