package com.bnorm.barkeep.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.activity.bar.BarListFragment;
import com.bnorm.barkeep.activity.book.BookListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FRAG_TAG = MainActivity.class.getName();

    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentManager = getSupportFragmentManager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragment = mFragmentManager.findFragmentByTag(FRAG_TAG);
        if (mFragment == null) {
            mFragment = new HomeFragment();
            mFragment.setArguments(getIntent().getExtras());
            mFragmentManager.beginTransaction().add(R.id.frame_main_content, mFragment, FRAG_TAG).commit();
            navigationView.setCheckedItem(R.id.main_nav_home);
        }
    }

    public DrawerLayout getDrawer() {
        return mDrawer;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_nav_home) {
            mFragment = new HomeFragment();
            mFragmentManager.beginTransaction().replace(R.id.frame_main_content, mFragment, FRAG_TAG).commit();
        } else if (id == R.id.main_nav_books) {
            mFragment = new BookListFragment();
            mFragmentManager.beginTransaction().replace(R.id.frame_main_content, mFragment, FRAG_TAG).commit();
        } else if (id == R.id.main_nav_bars) {
            mFragment = new BarListFragment();
            mFragmentManager.beginTransaction().replace(R.id.frame_main_content, mFragment, FRAG_TAG).commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
