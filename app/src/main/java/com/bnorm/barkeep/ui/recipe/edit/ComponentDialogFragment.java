package com.bnorm.barkeep.ui.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.bnorm.barkeep.data.api.model.Amount;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Ingredient;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;

// todo make this more dependent on the datamodel
public class ComponentDialogFragment extends AppCompatDialogFragment {

    public interface ComponentDialogListener {

        void onDialogPositiveClick(Integer i, Component component);

        void onDialogNegativeClick(Integer i, Component component);
    }

    private static final String POSITION_TEXT_ARG = "Position";
    private static final String COMPONENT_TEXT_ARG = "Component";
    private static final String NEGATIVE_TEXT_ARG = "NegativeText";

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

    public static void launch(FragmentManager supportFragmentManager, Integer position, Component component,
                              String negativeText) {
        Bundle args = new Bundle();
        args.putInt(POSITION_TEXT_ARG, position != null ? position : -1);
        args.putParcelable(COMPONENT_TEXT_ARG, component);
        args.putString(NEGATIVE_TEXT_ARG, negativeText);

        ComponentDialogFragment dialog = new ComponentDialogFragment();
        dialog.setArguments(args);
        dialog.show(supportFragmentManager, "ComponentDialogFragment");
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
        Bundle args = getArguments();
        String negativeText = args.getString(NEGATIVE_TEXT_ARG, "Cancel");
        mComponent = args.getParcelable(COMPONENT_TEXT_ARG);
        mComponent = mComponent != null ? mComponent : new Component();
        mLocation = args.getInt(POSITION_TEXT_ARG);
        mLocation = mLocation != -1 ? mLocation : null;


        // ===== Populate local fields ===== //
        LayoutInflater inflater = getActivity().getLayoutInflater();
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
