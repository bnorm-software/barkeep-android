package com.bnorm.barkeep.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.ArrayAdapter;

public class StableArrayAdapter extends ArrayAdapter<String> {

    private final Map<String, Integer> mIdMap = new HashMap<>();

    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
