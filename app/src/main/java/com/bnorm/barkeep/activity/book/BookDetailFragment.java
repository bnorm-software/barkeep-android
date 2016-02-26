package com.bnorm.barkeep.activity.book;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.Retained;
import com.bnorm.barkeep.server.data.store.Book;
import com.bnorm.barkeep.server.data.store.Recipe;

public class BookDetailFragment extends Fragment {

    private Book mBook;

    public void setBook(Book book) {
        this.mBook = book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBook = Retained.retain(this, "book", mBook);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mBook.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        if (mBook != null) {
            StringBuilder builder = new StringBuilder();
            if (mBook.getRecipes() != null) {
                for (Recipe recipe : mBook.getRecipes()) {
                    builder.append(recipe.getName()).append('\n');
                }
            }
            ((TextView) rootView.findViewById(R.id.book_detail)).setText(builder.toString());
        }

        return rootView;
    }
}
