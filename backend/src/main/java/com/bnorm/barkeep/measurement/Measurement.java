package com.bnorm.barkeep.measurement;

public class Measurement<T extends Unit<T>> {

    private final double amount;
    private final T unit;

    public Measurement(double amount, T unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public T getUnit() {
        return unit;
    }

    public Measurement<T> to(T newUnit) {
        return new Measurement<>(unit.convertTo(amount, newUnit), newUnit);
    }

    @Override
    public String toString() {
        return "Measurement{" + amount + " " + unit + '}';
    }
}
