package com.bnorm.barkeep.ui.book;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.data.api.model.Recipe;
import com.bnorm.barkeep.lib.BindingAdapters;
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
            adapter.items.clear();
            adapter.items.addAll(mBook.getRecipes());
        }

        return root;
    }

    public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeGridAdapter.Holder> {

        private final List<Recipe> items = new ArrayList<>();

        @Override
        public RecipeGridAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder = new Holder(parent);
            holder.itemView.setOnClickListener(v -> onRecipeClick(holder));
            return holder;
        }

        private void onRecipeClick(Holder holder) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                                                                               holder.recipeImage,
                                                                                               "recipeImage");
            ViewRecipeActivity.launch(getContext(), items.get(holder.getAdapterPosition()), options.toBundle());
        }

        @Override
        public void onBindViewHolder(RecipeGridAdapter.Holder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.recipe_title) TextView recipeTitle;
            @BindView(R.id.recipe_image) ImageView recipeImage;

            public Holder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_grid, parent, false));
                ButterKnife.bind(this, itemView);
            }

            public void bind(Recipe recipe) {
                recipeTitle.setText(recipe.getName());
                BindingAdapters.loadImage(recipeImage, recipe.getPicture());
            }
        }
    }
}
