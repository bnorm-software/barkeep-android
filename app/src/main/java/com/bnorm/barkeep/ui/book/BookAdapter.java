package com.bnorm.barkeep.ui.book;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.databinding.ItemBookBinding;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final BookListView view;
    private final List<Book> items;

    @Inject
    public BookAdapter(BookListView view) {
        this.view = view;
        this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(parent);
        holder.itemView.setOnClickListener(v -> onBookClick(holder.getAdapterPosition()));
        return holder;
    }

    private void onBookClick(int position) {
        view.onBookDetail(items.get(position));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void set(List<Book> books) {
        items.clear();
        items.addAll(books);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookBinding binding;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
            binding = ItemBookBinding.bind(itemView);
        }

        public void bind(Book book) {
            binding.setBook(book);
        }
    }
}
