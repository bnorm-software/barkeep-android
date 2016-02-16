package com.bnorm.barkeep.server.data.store.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import com.bnorm.barkeep.server.data.store.Endpoints;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.google.common.base.Preconditions;

public class RecipeListAsyncTask extends AsyncTask<String, Void, List<Recipe>> {

    @Override
    protected List<Recipe> doInBackground(String... params) {
        String name = Preconditions.checkNotNull(params[0]);
        try {
            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe> recipes;
            recipes = Endpoints.instance.getEndpoint().listRecipes().setName(name).execute().getItems();
            if (recipes != null) {
                List<Recipe> items = new ArrayList<>();
                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Recipe recipe : recipes) {
                    items.add(new Recipe(recipe));
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
