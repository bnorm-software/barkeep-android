package com.bnorm.barkeep.ui.bar.edit;

import com.bnorm.barkeep.data.api.model.Bar;

public interface EditBarView {

    void onClose();

    void onBarSaved(Bar bar);
}
