package com.bnorm.barkeep.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.ui.bar.BarListFragment;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.book.BookListFragment;
import com.bnorm.barkeep.ui.recipe.search.SearchRecipeActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAG_TAG = MainActivity.class.getName();

    // ===== View ===== //

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toggle = new ActionBarDrawerToggle(this,
                                           drawer,
                                           toolbar,
                                           R.string.navigation_drawer_open,
                                           R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAG_TAG);
        if (fragment == null) {
            fragment = new HomeFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.frame_main_content, fragment, FRAG_TAG).commit();
            navigationView.setCheckedItem(R.id.main_nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return toggle.onOptionsItemSelected(item);
        } else if (id == R.id.action_search) {
            SearchRecipeActivity.launch(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.main_nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.main_nav_books) {
            fragment = new BookListFragment();
        } else if (id == R.id.main_nav_bars) {
            fragment = new BarListFragment();
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.frame_main_content, fragment, FRAG_TAG)
                                       .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
