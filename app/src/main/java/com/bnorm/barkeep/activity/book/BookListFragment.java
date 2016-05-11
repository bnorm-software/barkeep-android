package com.bnorm.barkeep.activity.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.MainActivity;
import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivity;
import com.bnorm.barkeep.activity.recipe.search.SearchRecipeActivity;
import com.bnorm.barkeep.databinding.ItemBookBinding;
import com.bnorm.barkeep.server.data.store.Book;
import com.bnorm.barkeep.ui.base.fragment.BaseFragment;

public class BookListFragment extends BaseFragment {

    @BindView(R.id.fab) FloatingActionButton mFab;

    private MenuItem mSearch;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    private boolean mTwoPane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        mDrawer = activity.getDrawer();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Books");
        }

        mToggle = new ActionBarDrawerToggle(activity,
                                            mDrawer,
                                            (Toolbar) view.findViewById(R.id.main_toolbar),
                                            R.string.navigation_drawer_open,
                                            R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();
        setHasOptionsMenu(true);

        // todo should this only be available within a book?
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditRecipeActivity.class);
                startActivity(intent);
            }
        });


        final BookAdapter adapter = new BookAdapter();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        recyclerView.setAdapter(adapter);

        AsyncTask<Void, Void, List<Book>> task = new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... params) {
                try {
                    List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book> books;
                    books = component().endpoint().listBooks().execute().getItems();
                    if (books != null) {
                        List<Book> items = new ArrayList<>();
                        for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book book : books) {
                            items.add(new Book(book));
                        }
                        return items;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (IOException e) {
                    return Collections.emptyList();
                }
            }

            @Override
            protected void onPostExecute(List<Book> result) {
                adapter.set(result);
                adapter.notifyDataSetChanged();
            }
        };
        task.execute();

        if (view.findViewById(R.id.book_detail_container) != null) {
            mTwoPane = true;
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return mToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_home, menu);

        mSearch = menu.findItem(R.id.action_search);
        mSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getActivity(), SearchRecipeActivity.class);
                startActivity(intent);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

        private final List<Book> items;

        public BookAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_book, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.binding.setBook(items.get(position));
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

            public ViewHolder(View view) {
                super(view);
                binding = ItemBookBinding.bind(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            RecipeGridFragment fragment = new RecipeGridFragment();
                            fragment.setBook(binding.getBook());
                            getFragmentManager().beginTransaction()
                                                .replace(R.id.book_detail_container, fragment)
                                                .commit();
                        } else {
                            Context context = v.getContext();
                            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
                            //                                                               R.anim.slide_in_from_right,
                            //                                                               R.anim.slide_out_to_right);
                            Intent intent = new Intent(context, BookDetailActivity.class);
                            intent.putExtra(BookDetailActivity.BOOK_TAG, binding.getBook());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        }
    }
}
