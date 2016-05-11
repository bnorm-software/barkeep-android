package com.bnorm.barkeep.lib;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class ListBindingAdapter<E, B extends ViewDataBinding>
        extends RecyclerView.Adapter<ListBindingAdapter.BindingViewHolder<B>> {

    protected final List<E> items;

    public ListBindingAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<E> getItems() {
        return items;
    }

    public void add(E item) {
        items.add(item);
    }

    public void removeAt(int location) {
        items.remove(location);
    }

    public void set(int location, E item) {
        items.set(location, item);
    }

    public void addAll(@Nonnull List<? extends E> items) {
        this.items.addAll(items);
    }

    public void clear() {
        items.clear();
    }


    public void setAll(@Nonnull List<? extends E> items) {
        clear();
        addAll(items);
    }

    public static class BindingViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private final B binding;

        public BindingViewHolder(@Nonnull B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public B getBinding() {
            return binding;
        }
    }
}
