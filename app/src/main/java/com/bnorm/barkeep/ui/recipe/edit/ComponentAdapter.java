package com.bnorm.barkeep.ui.recipe.edit;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Amount;
import com.bnorm.barkeep.data.api.model.Component;
import com.bnorm.barkeep.data.api.model.Ingredient;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {

    private final EditRecipeView activity;
    private final List<Component> items;

    @Inject
    public ComponentAdapter(EditRecipeView activity) {
        this.activity = activity;
        this.items = new ArrayList<>();
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComponentViewHolder holder = new ComponentViewHolder(parent);
        holder.itemView.setOnClickListener(v -> onComponentClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    private void onComponentClick(int position) {
        activity.onEditComponent(position, items.get(position), "Delete");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Component> getItems() {
        return items;
    }

    public void add(Component component) {
        items.add(component);
    }

    public void remove(int location) {
        items.remove(location);
    }

    public void set(int location, Component component) {
        items.set(location, component);
    }

    public void set(List<Component> components) {
        items.clear();
        items.addAll(components);
    }

    public class ComponentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.component_edit_amount) TextView amount;
        @BindView(R.id.component_edit_ingredients) TextView ingredients;

        private ComponentViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_component, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(Component component) {
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

            this.amount.setText(amountBuilder);
            this.ingredients.setText(ingredientBuilder);
        }
    }
}
