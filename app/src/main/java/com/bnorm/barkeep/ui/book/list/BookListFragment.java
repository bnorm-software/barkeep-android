package com.bnorm.barkeep.ui.book.list;

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
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.ui.MainActivity;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.book.detail.BookDetailActivity;
import com.bnorm.barkeep.ui.book.detail.BookDetailFragment;
import com.bnorm.barkeep.ui.book.edit.EditBookActivity;

public class BookListFragment extends BaseFragment implements BookListView {

    // ===== View ===== //

    @BindView(R.id.book_list) RecyclerView recyclerView;
    @Nullable @BindView(R.id.bar_detail_container) FrameLayout detailContainer;

    // ===== Presenter ===== //

    @Inject BookListPresenter presenter;
    @Inject BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        barkeep().component().plus(new BookListViewModule(this)).inject(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Books");
        }

        recyclerView.setAdapter(adapter);
        presenter.loadBooks();
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
        EditBookActivity.launch(getContext());
    }

    @Override
    public void onBooks(List<Book> books) {
        adapter.set(books);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBookDetail(Book book) {
        if (detailContainer != null) {
            BookDetailFragment fragment = BookDetailFragment.create(book);
            getFragmentManager().beginTransaction().replace(detailContainer.getId(), fragment).commit();
        } else {
            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
            //                                                               R.anim.slide_in_from_right,
            //                                                               R.anim.slide_out_to_right);
            BookDetailActivity.launch(getContext(), book);
        }
    }
}
