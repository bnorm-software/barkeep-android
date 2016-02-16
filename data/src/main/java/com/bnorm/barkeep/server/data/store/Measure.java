package com.bnorm.barkeep.server.data.store;

import java.util.Collections;
import java.util.List;

import com.bnorm.barkeep.measurement.Unit;
import com.bnorm.barkeep.measurement.type.Unitless;
import com.bnorm.barkeep.server.data.store.v1.Amount;
import com.bnorm.barkeep.server.data.store.v1.Ingredient;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

public final class Measure {
    /** Private constructor for utility class. */
    private Measure() {
    }

    public static ComponentBuilder<Unitless> need(Ingredient ingredient) {
        return out(0.0, Unitless.Unitless).of(ingredient);
    }

    public static <T extends Unit<T>> ComponentBuilder<T> out(double recommended, Unit<T> unit) {
        Amount amount = new Amount();
        amount.setRecommended(recommended);
        amount.setMin(recommended);
        amount.setMax(recommended);
        return new ComponentBuilder<>(amount, unit);
    }

    public static <T extends Unit<T>> ComponentBuilder<T> out(double min, double max, Unit<T> unit) {
        Amount amount = new Amount();
        amount.setMin(min);
        amount.setMax(max);
        amount.setRecommended((min + max) / 2.0);
        return new ComponentBuilder<>(amount, unit);
    }

    public static class ComponentBuilder<T extends Unit<T>> {

        private final Amount amount;
        private final Unit<T> unit;
        private List<Ingredient> ingredients = Collections.emptyList();

        private ComponentBuilder(Amount amount, Unit<T> unit) {
            this.amount = amount;
            this.unit = unit;
        }

        public ComponentBuilder<T> of(Ingredient ingredient, Ingredient... or) {
            this.ingredients = Lists.asList(ingredient, or);
            return this;
        }

        Amount getAmount() {
            return amount;
        }

        Unit<T> getUnit() {
            return unit;
        }

        List<Ingredient> getIngredients() {
            return ingredients;
        }
    }
}
