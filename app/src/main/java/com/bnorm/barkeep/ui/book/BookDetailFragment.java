package com.bnorm.barkeep.ui.book;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.databinding.ItemRecipeGridBinding;
import com.bnorm.barkeep.lib.ListBindingAdapter;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.ViewRecipeActivity;

public class BookDetailFragment extends BaseFragment {

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
        View root = inflater.inflate(R.layout.fragment_book_detail, container, false);

        RecyclerView gridview = (RecyclerView) root.findViewById(R.id.recipe_grid);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        gridview.setLayoutManager(manager);

        RecipeGridAdapter adapter = new RecipeGridAdapter();
        gridview.setAdapter(adapter);
        if (mBook != null) {
            adapter.setAll(mBook.getRecipes());
        }

        return root;
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
