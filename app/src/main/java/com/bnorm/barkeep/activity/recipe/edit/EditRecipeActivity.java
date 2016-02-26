package com.bnorm.barkeep.activity.recipe.edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Recipe;
import com.bnorm.barkeep.server.data.store.task.SaveRecipeAsyncTask;

public class EditRecipeActivity extends AppCompatActivity implements ComponentDialogFragment.ComponentDialogListener {
    public static final String RECIPE_TAG = EditRecipeActivity.class.getName() + ".recipe";

    // ===== Model ===== //

    private Recipe mRecipe;

    private ComponentAdapter mComponentAdapter;


    // ===== View ===== //

    @Bind(R.id.create_recipe_cancel) AppCompatImageButton mCancel;
    @Bind(R.id.create_recipe_save) AppCompatButton mSave;
    @Bind(R.id.create_recipe_name) AppCompatEditText mName;
    @Bind(R.id.create_recipe_description) AppCompatEditText mDescription;
    @Bind(R.id.create_recipe_directions) AppCompatEditText mDirections;
    @Bind(R.id.create_recipe_components) RecyclerView mComponents;
    @Bind(R.id.create_recipe_add_component) AppCompatButton mAddComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        // find the retained fragment on activity restarts
        if (bundle != null) {
            mRecipe = bundle.getParcelable(RECIPE_TAG);
        }
        if (mRecipe == null) {
            mRecipe = new Recipe();
        }


        // ===== Configure toolbar ===== //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo are you sure popup
                onBackPressed();
                //                NavUtils.navigateUpTo(EditRecipeActivity.this, getIntent());
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo make sure all required fields are filled
                onBackPressed();
                //                NavUtils.navigateUpTo(EditRecipeActivity.this, getIntent());
                Recipe recipe = new Recipe();
                recipe.setName(mName.getText().toString());
                recipe.setName(mDescription.getText().toString());
                recipe.setName(mDirections.getText().toString());
                recipe.setComponents(mComponentAdapter.getItems());
                // todo add ingredients to the database
                // todo save the recipe to the database
                SaveRecipeAsyncTask task = new SaveRecipeAsyncTask() {
                    @Override
                    protected void onPostExecute(Boolean result) {
                    }
                };
                task.execute(mRecipe);
                Toast.makeText(getApplicationContext(), "Saved " + mName.getText(), Toast.LENGTH_LONG).show();
            }
        });


        // ===== Configure ? ===== //
        mComponentAdapter = new ComponentAdapter(this);
        mComponents.setAdapter(mComponentAdapter);
        mComponents.setNestedScrollingEnabled(false);
        mComponents.setItemAnimator(null);
        mComponents.setLayoutManager(new WrappingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAddComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(ComponentDialogFragment.NEGATIVE_TEXT_ARG, "Cancel");

                ComponentDialogFragment dialog = new ComponentDialogFragment();
                dialog.setArguments(args);
                dialog.setComponent(new Component());
                dialog.show(getSupportFragmentManager(), "ComponentDialogFragment");
            }
        });


        // ==== Populate ? ===== //

        mName.setText(mRecipe.getName());
        mDescription.setText(mRecipe.getDescription());
        mDirections.setText(mRecipe.getDirections());
        mComponentAdapter.set(mRecipe.getComponents());
        mComponentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // store the data in the fragment
        //        updateRecipe();
    }

    @Override
    public void onDialogPositiveClick(Integer i, Component component) {
        if (i == null) {
            mComponentAdapter.add(component);
            mComponentAdapter.notifyItemInserted(mComponentAdapter.getItemCount() - 1);
        } else {
            mComponentAdapter.set(i, component);
            mComponentAdapter.notifyItemChanged(i);
        }
    }

    @Override
    public void onDialogNegativeClick(Integer i, Component component) {
        if (i != null) {
            mComponentAdapter.remove(i);
            mComponentAdapter.notifyItemRemoved(i);
        }
    }
}
