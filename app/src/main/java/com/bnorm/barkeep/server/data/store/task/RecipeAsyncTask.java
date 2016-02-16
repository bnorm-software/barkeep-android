package com.bnorm.barkeep.server.data.store.task;

import java.io.IOException;

import android.os.AsyncTask;
import com.bnorm.barkeep.server.data.store.Endpoints;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.google.common.base.Preconditions;

public class RecipeAsyncTask extends AsyncTask<String, Void, Recipe> {

    @Override
    protected Recipe doInBackground(String... params) {
        String name = Preconditions.checkNotNull(params[0]);
        try {
            return new Recipe(Endpoints.instance.getEndpoint().getRecipe(name).execute());
        } catch (IOException e) {
            return null;
        }
    }
}
