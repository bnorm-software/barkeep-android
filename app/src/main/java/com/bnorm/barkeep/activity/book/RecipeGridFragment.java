package com.bnorm.barkeep.activity.book;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Book;
import com.bnorm.barkeep.ui.base.fragment.BaseFragment;

public class RecipeGridFragment extends BaseFragment {

    private Book mBook;

    public void setBook(Book book) {
        this.mBook = book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null && mBook != null) {
            appBarLayout.setTitle(mBook.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_grid, container, false);

        RecyclerView gridview = (RecyclerView) root.findViewById(R.id.recipe_grid);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        gridview.setLayoutManager(manager);

        RecipeGridAdapter adapter = new RecipeGridAdapter(getActivity());
        gridview.setAdapter(adapter);
        if (mBook != null) {
            adapter.set(mBook.getRecipes());
        }

        return root;
    }
}
