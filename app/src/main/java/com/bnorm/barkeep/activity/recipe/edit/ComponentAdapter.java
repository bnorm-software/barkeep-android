package com.bnorm.barkeep.activity.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.server.data.store.Amount;
import com.bnorm.barkeep.server.data.store.Component;
import com.bnorm.barkeep.server.data.store.Ingredient;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {

    private final AppCompatActivity activity;
    private final List<Component> mItems;

    public ComponentAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.mItems = new ArrayList<>();
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_component, parent, false);
        return new ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        Component component = mItems.get(position);

        StringBuilder amountBuilder = new StringBuilder();
        Amount amount = component.getAmount();
        if (amount.getRecommended() != null) {
            amountBuilder.append(amount.getRecommended());
        } else {
            amountBuilder.append(amount.getMin());
            amountBuilder.append(" to ");
            amountBuilder.append(amount.getMax());
        }
        amountBuilder.append(" ");
        amountBuilder.append(component.getUnit());
        amountBuilder.append(" ");

        StringBuilder ingredientBuilder = new StringBuilder();
        amountBuilder.append("of ");
        boolean first = true;
        for (Ingredient ingredient : component.getIngredients()) {
            if (!first) {
                ingredientBuilder.append("\nor ");
            }
            ingredientBuilder.append(ingredient.getName());
            first = false;
        }

        holder.mAmount.setText(amountBuilder);
        holder.mIngredients.setText(ingredientBuilder);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<Component> getItems() {
        return mItems;
    }

    public void add(Component component) {
        mItems.add(component);
    }

    public void remove(int location) {
        mItems.remove(location);
    }

    public void set(int location, Component component) {
        mItems.set(location, component);
    }

    public void set(List<Component> components) {
        mItems.clear();
        mItems.addAll(components);
    }

    public class ComponentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.component_edit_amount) TextView mAmount;
        @BindView(R.id.component_edit_ingredients) TextView mIngredients;

        private ComponentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Component component = mItems.get(position);

                    Bundle args = new Bundle();
                    args.putString(ComponentDialogFragment.NEGATIVE_TEXT_ARG, "Delete");

                    ComponentDialogFragment dialog = new ComponentDialogFragment();
                    dialog.setArguments(args);
                    dialog.setComponent(component);
                    dialog.setLocation(position);

                    dialog.show(activity.getSupportFragmentManager(), "ComponentDialogFragment");
                }
            });
        }
    }
}
