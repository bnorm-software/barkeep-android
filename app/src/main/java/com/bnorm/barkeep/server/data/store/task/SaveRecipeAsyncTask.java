package com.bnorm.barkeep.server.data.store.task;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
import com.bnorm.barkeep.server.data.store.Endpoints;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.v1.endpoint.Endpoint;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveRecipeAsyncTask extends AsyncTask<Recipe, Void, Boolean> {

    private static final Logger log = LoggerFactory.getLogger(SaveRecipeAsyncTask.class);

    @Override
    protected Boolean doInBackground(Recipe... params) {
        Recipe recipe = Preconditions.checkNotNull(params[0]);
        Endpoint endpoint = Endpoints.instance.getEndpoint();
        boolean exists = false;
        try {
            Endpoint.GetRecipe request = endpoint.getRecipe(recipe.getName());
            request.execute();
        } catch (IOException e) {
            log.warn("Unable to retrieve recipe", e);
            exists = true;
        }
        if (exists) {
            try {
                Endpoint.UpdateRecipe request = endpoint.updateRecipe(recipe.getName(), recipe.toStore());
                request.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                Endpoint.InsertRecipe request = endpoint.insertRecipe(recipe.toStore());
                request.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
