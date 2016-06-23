package com.bnorm.barkeep.ui.base;

import com.bnorm.barkeep.BarkeepApp;
import com.trello.navi.component.support.NaviAppCompatActivity;

public abstract class BaseActivity extends NaviAppCompatActivity {

    protected BarkeepApp barkeep() {
        return (BarkeepApp) getApplication();
    }
}
