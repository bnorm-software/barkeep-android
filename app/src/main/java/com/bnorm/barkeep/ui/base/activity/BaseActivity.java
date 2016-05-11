package com.bnorm.barkeep.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.bnorm.barkeep.BarkeepApp;
import com.bnorm.barkeep.inject.activity.ActivityComponent;
import com.bnorm.barkeep.inject.activity.DaggerActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder()
                                           .appComponent(((BarkeepApp) getApplication()).component())
                                           .build();
    }

    public ActivityComponent component() {
        return component;
    }
}
