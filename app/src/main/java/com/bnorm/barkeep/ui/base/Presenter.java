package com.bnorm.barkeep.ui.base;

public interface Presenter<V> {
    void attach(V view);

    void detach();
}
