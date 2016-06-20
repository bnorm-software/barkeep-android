package com.bnorm.barkeep.ui.base.activity;

import android.os.Bundle;
import com.bnorm.barkeep.BarkeepApp;
import com.bnorm.barkeep.inject.activity.ActivityComponent;
import com.bnorm.barkeep.inject.activity.DaggerActivityComponent;
import com.trello.navi.component.support.NaviAppCompatActivity;

public abstract class BaseActivity extends NaviAppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder().appComponent(barkeep().component()).build();
    }

    protected BarkeepApp barkeep() {
        return (BarkeepApp) getApplication();
    }

    public ActivityComponent component() {
        return component;
    }
}
