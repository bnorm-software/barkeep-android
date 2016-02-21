package com.bnorm.barkeep.activity.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ViewSwitcher;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.Retained;
import com.bnorm.barkeep.lib.WrappingLinearLayoutManager;
import com.bnorm.barkeep.server.data.store.Amount;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Ingredient;

// todo make this more dependent on the datamodel
public class ComponentDialogFragment extends AppCompatDialogFragment {

    public interface ComponentDialogListener {

        void onDialogPositiveClick(Integer i, Component component);

        void onDialogNegativeClick(Integer i, Component component);
    }

    public static final String NEGATIVE_TEXT_ARG = "NegativeText";

    private Component mComponent;
    private Integer mLocation;

    @Bind(R.id.component_edit_range_view_switcher) ViewSwitcher mRangeViewSwitcher;
    @Bind(R.id.component_edit_range_switch) Switch mRangeSwitch;
    @Bind(R.id.component_edit_amount_recommended) EditText mAmountRecommended;
    @Bind(R.id.component_edit_amount_min) EditText mAmountMin;
    @Bind(R.id.component_edit_amount_max) EditText mAmountMax;
    @Bind(R.id.component_edit_unit_spinner) Spinner mUnitSelect;
    @Bind(R.id.component_edit_ingredients) RecyclerView mIngredients;
    private ArrayAdapter<CharSequence> mUnitAdapter;
    private IngredientAdapter mIngredientAdapter;
    private ComponentDialogListener mListener;


    public void setComponent(Component component) {
        this.mComponent = component;
    }

    public void setLocation(Integer location) {
        this.mLocation = location;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ComponentDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String negativeText = getArguments().getString(NEGATIVE_TEXT_ARG, "Cancel");

        // find the retained fragment on activity restarts
        mComponent = Retained.<Component>init(this, "component").get(mComponent != null ? mComponent : new Component());

        mLocation = Retained.<Integer>init(this, "location").get(mLocation);


        // ===== Populate local fields ===== //
        View view = inflater.inflate(R.layout.dialog_edit_component, null);
        ButterKnife.bind(this, view);

        mUnitAdapter = ArrayAdapter.createFromResource(getActivity(),
                                                       R.array.units,
                                                       android.R.layout.simple_spinner_item);
        mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitSelect.setAdapter(mUnitAdapter);

        mIngredientAdapter = new IngredientAdapter(mIngredients);
        mIngredients.setLayoutManager(new WrappingLinearLayoutManager(view.getContext(),
                                                                      LinearLayoutManager.VERTICAL,
                                                                      false));
        mIngredients.setNestedScrollingEnabled(false);
        mIngredients.setItemAnimator(null);
        mIngredients.setAdapter(mIngredientAdapter);


        // ===== Populate ? ===== //
        Amount amount = mComponent.getAmount();
        if (amount == null) {
            amount = new Amount();
            mComponent.setAmount(amount);
        }

        boolean range = amount.getMin() != null || amount.getMax() != null;
        mRangeSwitch.setChecked(range);
        mRangeViewSwitcher.setDisplayedChild(range ? 1 : 0);

        if (amount.getRecommended() != null) {
            mAmountRecommended.setText(amount.getRecommended().toString());
        }
        if (amount.getMin() != null) {
            mAmountMin.setText(amount.getMin().toString());
        }
        if (amount.getMax() != null) {
            mAmountMax.setText(amount.getMax().toString());
        }

        if (mComponent.getUnit() != null) {
            mUnitSelect.setSelection(mUnitAdapter.getPosition(mComponent.getUnit()));
        }

        List<Ingredient> ingredients = mComponent.getIngredients();
        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                mIngredientAdapter.add(ingredient.getName());
            }
        }
        mIngredientAdapter.add("");
        mIngredientAdapter.notifyDataSetChanged();


        // ===== Configure ? ===== //

        mRangeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRangeViewSwitcher.showNext();
            }
        });


        // ===== Build dialog ===== //

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Component");
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateComponent();
                mListener.onDialogPositiveClick(mLocation, mComponent);
            }
        });
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(mLocation, mComponent);
            }
        });
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // store the data in the fragment
        updateComponent();
    }

    private void updateComponent() {
        Amount amount = mComponent.getAmount();

        if (!mRangeSwitch.isChecked()) {
            String recommended = mAmountRecommended.getText().toString();

            amount.setRecommended(recommended.isEmpty() ? null : Double.valueOf(recommended));
            amount.setMin(null);
            amount.setMax(null);
        } else {
            String min = mAmountMin.getText().toString();
            String max = mAmountMax.getText().toString();

            amount.setRecommended(null);
            amount.setMin(min.isEmpty() ? null : Double.valueOf(min));
            amount.setMax(max.isEmpty() ? null : Double.valueOf(max));
        }

        mComponent.setUnit(mUnitSelect.getSelectedItem().toString());

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < mIngredientAdapter.getItemCount(); i++) {
            String item = mIngredientAdapter.get(i);
            if (!item.isEmpty()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(item);
                ingredients.add(ingredient);
            }
        }

        mComponent.setIngredients(ingredients);
    }
}
