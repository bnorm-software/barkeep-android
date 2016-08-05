package com.bnorm.barkeep.ui.book;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.databinding.ItemRecipeGridBinding;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.lib.ListBindingAdapter;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.ViewRecipeActivity;
import com.trello.navi.Event;
import com.trello.navi.rx.RxNavi;

public class BookDetailFragment extends BaseFragment {
    private static final String BOOK_TAG = "book";

    // ===== View ===== //

    @BindView(R.id.recipe_grid) RecyclerView recyclerView;


    public static BookDetailFragment create(Book book) {
        Bundle args = new Bundle();
        args.putParcelable(BOOK_TAG, book);

        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ButterKnife.bind(this, view);

        Book book = Bundles.getParcelable(BOOK_TAG,
                                          getArguments(),
                                          getActivity().getIntent().getExtras(),
                                          savedInstanceState);
        assert book != null;
        RxNavi.observe(this, Event.SAVE_INSTANCE_STATE).subscribe(outState -> outState.putParcelable(BOOK_TAG, book));

        RecipeGridAdapter adapter = new RecipeGridAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter.setAll(book.getRecipes());

        return view;
    }

    private class RecipeGridAdapter extends ListBindingAdapter<Recipe, ItemRecipeGridBinding> {

        @Override
        public BindingViewHolder<ItemRecipeGridBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemRecipeGridBinding binding = ItemRecipeGridBinding.inflate(inflater, parent, false);
            binding.getRoot().setOnClickListener(view -> {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                                                                                   binding.recipeImage,
                                                                                                   "recipeImage");
                ViewRecipeActivity.launch(view.getContext(), binding.getRecipe(), options.toBundle());
            });
            return new BindingViewHolder<>(binding);
        }

        @Override
        public void onBindViewHolder(BindingViewHolder<ItemRecipeGridBinding> holder, int position) {
            holder.getBinding().setRecipe(items.get(position));
        }
    }
}
