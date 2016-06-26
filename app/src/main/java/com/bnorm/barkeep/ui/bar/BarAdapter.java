package com.bnorm.barkeep.ui.bar;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.databinding.ItemBarBinding;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

    private final BarListView view;
    private final List<Bar> items;

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
        private final ItemBarBinding binding;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar, parent, false));
            binding = ItemBarBinding.bind(itemView);
        }

        public void bind(Bar bar) {
            binding.setBar(bar);
        }
    }
}
