package com.bnorm.barkeep.ui.base;

import com.bnorm.barkeep.BarkeepApp;
import com.trello.navi2.component.support.NaviFragment;

public abstract class BaseFragment extends NaviFragment {

    protected BarkeepApp barkeep() {
        return (BarkeepApp) getActivity().getApplication();
    }
}
