package com.bnorm.barkeep.ui.bar;

import java.util.List;

import com.bnorm.barkeep.data.api.model.Bar;

public interface BarListView {

    void onBars(List<Bar> bars);

    void onBarDetail(Bar bar);
}
