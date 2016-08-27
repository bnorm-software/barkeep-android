package com.bnorm.barkeep.data.api.model;

import java.util.ArrayList;
import java.util.List;

public class GaeList<E> {

    private List<E> items;

    public GaeList() {
        this.items = new ArrayList<>();
    }

    public GaeList(List<E> items) {
        this.items = items;
    }

    public List<E> getItems() {
        return items;
    }
}
