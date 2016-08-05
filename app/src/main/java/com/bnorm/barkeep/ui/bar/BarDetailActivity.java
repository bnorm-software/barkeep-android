package com.bnorm.barkeep.ui.bar;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.bar.edit.EditBarActivity;
import com.bnorm.barkeep.ui.base.BaseActivity;

public class BarDetailActivity extends BaseActivity {
    public static final String BAR_TAG = "bar";

    // ===== Model ===== //

    private Bar bar;

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    public static void launch(Context context, Bar bar) {
        Intent intent = new Intent(context, BarDetailActivity.class);
        intent.putExtra(BarDetailActivity.BAR_TAG, bar);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barkeep().component().plus(new BarDetailViewModule()).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_bar_detail, container);
        ButterKnife.bind(this, container);

        bar = Bundles.getParcelable(BAR_TAG, getIntent().getExtras());
        assert bar != null;

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(bar.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        EditBarActivity.launch(this, bar);
    }
}
