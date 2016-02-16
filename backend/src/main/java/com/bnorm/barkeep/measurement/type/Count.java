package com.bnorm.barkeep.measurement.type;

import com.bnorm.barkeep.measurement.Unit;
import com.bnorm.barkeep.measurement.UnitType;

public enum Count implements Unit<Count> {
    Count {
        @Override
        public double convertFrom(double amount, Count unit) {
            switch (unit) {
                case Count:
                    return amount;
            }
            return super.convertFrom(amount, unit);
        }
    },

    // End of enumeration
    ;


    @Override
    public UnitType getType() {
        return UnitType.Count;
    }

    @Override
    public double convertFrom(double amount, Count unit) {
        throw new IllegalArgumentException("Conversion from [" + unit + "] to [" + this + "] is not supported.");
    }

    @Override
    public double convertTo(double amount, Count unit) {
        return unit.convertFrom(amount, this);
    }
}
