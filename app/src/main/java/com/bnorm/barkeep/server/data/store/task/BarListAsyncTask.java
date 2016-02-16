package com.bnorm.barkeep.server.data.store.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import com.bnorm.barkeep.server.data.store.Bar;
import com.bnorm.barkeep.server.data.store.Book;
import com.bnorm.barkeep.server.data.store.Endpoints;

public class BarListAsyncTask extends AsyncTask<Void, Void, List<Bar>> {

    @Override
    protected List<Bar> doInBackground(Void... params) {
        try {
            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar> bars;
            bars = Endpoints.instance.getEndpoint().listBars().execute().getItems();
            if (bars != null) {
                List<Bar> items = new ArrayList<>();
                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Bar bar : bars) {
                    items.add(new Bar(bar));
                }
                return items;
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
