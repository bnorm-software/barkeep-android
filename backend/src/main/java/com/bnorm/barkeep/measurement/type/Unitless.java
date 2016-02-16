package com.bnorm.barkeep.measurement.type;

import com.bnorm.barkeep.measurement.Unit;
import com.bnorm.barkeep.measurement.UnitType;

public enum Unitless implements Unit<Unitless> {
    Unitless {
        @Override
        public double convertFrom(double amount, Unitless unit) {
            switch (unit) {
                case Unitless:
                    return amount;
            }
            return super.convertFrom(amount, unit);
        }
    },

    // End of enumeration
    ;


    @Override
    public UnitType getType() {
        return UnitType.Unitless;
    }

    @Override
    public double convertFrom(double amount, Unitless unit) {
        throw new IllegalArgumentException("Conversion from [" + unit + "] to [" + this + "] is not supported.");
    }

    @Override
    public double convertTo(double amount, Unitless unit) {
        return unit.convertFrom(amount, this);
    }
}
