package com.bnorm.barkeep.ui.bar;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.bar.edit.EditBarActivity;
import com.bnorm.barkeep.ui.base.BaseActivity;

public class BarDetailActivity extends BaseActivity {

    public static final String BAR_TAG = BarDetailActivity.class.getName() + ".bar";

    // ===== View ===== //

    @Inject ViewContainer viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barkeep().component().plus(new BarDetailViewModule()).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_bar_detail, container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Bar bar = getIntent().getParcelableExtra(BAR_TAG);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            assert bar != null;
            assert fab != null;

            fab.setOnClickListener(view -> EditBarActivity.launch(this, bar));

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            BarDetailFragment fragment = new BarDetailFragment();
            fragment.setBar(bar);
            getSupportFragmentManager().beginTransaction().add(R.id.bar_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
