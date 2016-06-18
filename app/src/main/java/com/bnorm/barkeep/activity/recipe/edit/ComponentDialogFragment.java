package com.bnorm.barkeep.activity.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.lib.Retained;
import com.bnorm.barkeep.server.data.store.Amount;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Ingredient;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.Observable;

// todo make this more dependent on the datamodel
public class ComponentDialogFragment extends AppCompatDialogFragment {

    public interface ComponentDialogListener {

        void onDialogPositiveClick(Integer i, Component component);

        void onDialogNegativeClick(Integer i, Component component);
    }

    public static final String NEGATIVE_TEXT_ARG = "NegativeText";

    private Component mComponent;
    private Integer mLocation;

    @BindView(R.id.component_edit_range_view_switcher) ViewSwitcher mRangeViewSwitcher;
    @BindView(R.id.component_edit_range_switch) Switch mRangeSwitch;
    @BindView(R.id.component_edit_amount_recommended) EditText mAmountRecommended;
    @BindView(R.id.component_edit_amount_min) EditText mAmountMin;
    @BindView(R.id.component_edit_amount_max) EditText mAmountMax;
    @BindView(R.id.component_edit_unit_spinner) Spinner mUnitSelect;
    @BindView(R.id.component_edit_ingredients) RecyclerView mIngredients;
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
        // todo is there a better way to get this?  dagger?
        mListener = ((EditRecipeActivity) activity).presenter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String negativeText = getArguments().getString(NEGATIVE_TEXT_ARG, "Cancel");

        // find the retained fragment on activity restarts
        mComponent = Retained.retain(this, "component", mComponent != null ? mComponent : new Component());
        mLocation = Retained.retain(this, "location", mLocation);


        // ===== Populate local fields ===== //
        View view = inflater.inflate(R.layout.dialog_edit_component, null);
        ButterKnife.bind(this, view);

        RxTextView.textChanges(mAmountRecommended).subscribe(recommendedText -> {
            Amount amount = mComponent.getAmount();
            if (!mRangeSwitch.isChecked()) {
                String recommended = recommendedText.toString();
                amount.setRecommended(recommended.isEmpty() ? null : Double.valueOf(recommended));
                amount.setMin(null);
                amount.setMax(null);
            }
        });
        Observable.combineLatest(RxTextView.textChanges(mAmountMin),
                                 RxTextView.textChanges(mAmountMax),
                                 (minText, maxText) -> {
                                     Amount amount = mComponent.getAmount();
                                     if (mRangeSwitch.isChecked()) {
                                         String min = minText.toString();
                                         String max = maxText.toString();
                                         amount.setRecommended(null);
                                         amount.setMin(min.isEmpty() ? null : Double.valueOf(min));
                                         amount.setMax(max.isEmpty() ? null : Double.valueOf(max));
                                     }
                                     return amount;
                                 }).subscribe();


        mUnitAdapter = ArrayAdapter.createFromResource(getActivity(),
                                                       R.array.units,
                                                       android.R.layout.simple_spinner_item);
        mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitSelect.setAdapter(mUnitAdapter);

        mIngredientAdapter = new IngredientAdapter(mIngredients);
        mIngredients.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
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

        RxView.clicks(mRangeViewSwitcher).subscribe(aVoid -> {
            mRangeViewSwitcher.showNext();
        });


        // ===== Build dialog ===== //

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Component");
        builder.setView(view);
        builder.setPositiveButton("Save", (dialog, id) -> {
            updateComponent();
            mListener.onDialogPositiveClick(mLocation, mComponent);
        });
        builder.setNegativeButton(negativeText, (dialog, id) -> {
            mListener.onDialogNegativeClick(mLocation, mComponent);
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
            String item = mIngredientAdapter.get(i).toString();
            if (!item.isEmpty()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(item);
                ingredients.add(ingredient);
            }
        }

        mComponent.setIngredients(ingredients);
    }
}
