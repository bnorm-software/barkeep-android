package com.bnorm.barkeep.ui.book.detail;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.base.BaseFragment;
import com.bnorm.barkeep.ui.recipe.detail.ViewRecipeActivity;
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

        public void setAll(List<Recipe> recipes) {
            items.clear();
            items.addAll(recipes);
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
                BindingAdapters.loadImage(recipeImage,
                                          "http://dummyimage.com/400x400/000/fff.png&text=" + recipe.getName().replace(' ', '+'));
            }
        }
    }
}
