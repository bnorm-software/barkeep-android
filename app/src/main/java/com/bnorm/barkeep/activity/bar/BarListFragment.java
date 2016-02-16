package com.bnorm.barkeep.activity.bar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.MainActivity;
import com.bnorm.barkeep.activity.recipe.edit.EditRecipeActivity;
import com.bnorm.barkeep.activity.recipe.search.SearchRecipeActivity;
import com.bnorm.barkeep.server.data.store.Bar;
import com.bnorm.barkeep.server.data.store.task.BarListAsyncTask;

public class BarListFragment extends Fragment {

    @Bind(R.id.fab) FloatingActionButton mFab;

    private MenuItem mSearch;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    private boolean mTwoPane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_list, container, false);

        MainActivity activity = (MainActivity) getActivity();
        mDrawer = activity.getDrawer();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Bars");
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
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditRecipeActivity.class);
                startActivity(intent);
            }
        });


        final BarAdapter adapter = new BarAdapter();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bar_list);
        recyclerView.setAdapter(adapter);

        BarListAsyncTask task = new BarListAsyncTask() {
            @Override
            protected void onPostExecute(List<Bar> result) {
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

    public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {

        private final List<Bar> mItems;

        public BarAdapter() {
            mItems = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_book, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Bar item = mItems.get(position);
            holder.mItem = item;
            holder.mTitle.setText(item.getName());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void set(List<Bar> books) {
            mItems.clear();
            mItems.addAll(books);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.book_title) TextView mTitle;
            public Bar mItem;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            IngredientShelvesFragment fragment = new IngredientShelvesFragment();
                            fragment.setBar(mItem);
                            getFragmentManager().beginTransaction()
                                                .replace(R.id.book_detail_container, fragment)
                                                .commit();
                        } else {
                            Context context = v.getContext();
                            // ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
                            //                                                               R.anim.slide_in_from_right,
                            //                                                               R.anim.slide_out_to_right);
                            Intent intent = new Intent(context, BarDetailActivity.class);
                            intent.putExtra(BarDetailActivity.BAR_TAG, mItem);
                            context.startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitle.getText() + "'";
            }
        }
    }
}
