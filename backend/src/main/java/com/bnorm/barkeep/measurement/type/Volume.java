package com.bnorm.barkeep.measurement.type;

import com.bnorm.barkeep.measurement.Unit;
import com.bnorm.barkeep.measurement.UnitType;

public enum Volume implements Unit<Volume> {
    Litre {
        @Override
        public double convertFrom(double amount, Volume unit) {
            switch (unit) {
                case Litre:
                    return amount;
                case Ounce:
                    return amount * 0.0295735;
                case Cup:
                    return amount * 0.236588;
                case Pint:
                    return amount * 0.473176;
                case Quart:
                    return amount * 0.946353;
            }
            return super.convertFrom(amount, unit);
        }
    },

    Ounce {
        @Override
        public double convertFrom(double amount, Volume unit) {
            switch (unit) {
                case Litre:
                    return amount * 33.814;
                case Ounce:
                    return amount;
                case Cup:
                    return amount * 8;
                case Pint:
                    return amount * 16;
                case Quart:
                    return amount * 32;
            }
            return super.convertFrom(amount, unit);
        }
    },

    Cup {
        @Override
        public double convertFrom(double amount, Volume unit) {
            switch (unit) {
                case Litre:
                    return amount * 4.22675;
                case Ounce:
                    return amount * 0.125;
                case Cup:
                    return amount;
                case Pint:
                    return amount * 2;
                case Quart:
                    return amount * 4;
            }
            return super.convertFrom(amount, unit);
        }
    },

    Pint {
        @Override
        public double convertFrom(double amount, Volume unit) {
            switch (unit) {
                case Litre:
                    return amount * 2.11338;
                case Ounce:
                    return amount * 0.0625;
                case Cup:
                    return amount * 0.5;
                case Pint:
                    return amount;
                case Quart:
                    return amount * 2;
            }
            return super.convertFrom(amount, unit);
        }
    },

    Quart {
        @Override
        public double convertFrom(double amount, Volume unit) {
            switch (unit) {
                case Litre:
                    return amount * 1.05669;
                case Ounce:
                    return amount * 0.03125;
                case Cup:
                    return amount * 0.25;
                case Pint:
                    return amount * 0.5;
                case Quart:
                    return amount;
            }
            return super.convertFrom(amount, unit);
        }
    },

    Teaspoon,

    Dash,

    // End of enumeration
    ;


    @Override
    public UnitType getType() {
        return UnitType.Volume;
    }

    @Override
    public double convertFrom(double amount, Volume unit) {
        throw new IllegalArgumentException("Conversion from [" + unit + "] to [" + this + "] is not supported.");
    }

    @Override
    public double convertTo(double amount, Volume unit) {
        return unit.convertFrom(amount, this);
    }
}
