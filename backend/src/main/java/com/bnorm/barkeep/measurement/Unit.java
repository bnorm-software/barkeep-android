package com.bnorm.barkeep.measurement;

public interface Unit<T extends Unit<T>> {

    UnitType getType();

    double convertFrom(double amount, T unit);

    double convertTo(double amount, T unit);
}
