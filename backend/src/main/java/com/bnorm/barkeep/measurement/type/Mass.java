package com.bnorm.barkeep.measurement.type;

import com.bnorm.barkeep.measurement.Unit;
import com.bnorm.barkeep.measurement.UnitType;

public enum Mass implements Unit<Mass> {
    Teaspoon,
    Pinch,

    // End of enumeration
    ;


    @Override
    public UnitType getType() {
        return UnitType.Mass;
    }

    @Override
    public double convertFrom(double amount, Mass unit) {
        throw new IllegalArgumentException("Conversion from [" + unit + "] to [" + this + "] is not supported.");
    }

    @Override
    public double convertTo(double amount, Mass unit) {
        return unit.convertFrom(amount, this);
    }
}
