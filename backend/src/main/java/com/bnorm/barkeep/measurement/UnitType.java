package com.bnorm.barkeep.measurement;

public enum UnitType {
    Length(),

    Area(),

    Volume() {
        @Override
        public com.bnorm.barkeep.measurement.type.Volume parse(String str) {
            return com.bnorm.barkeep.measurement.type.Volume.valueOf(str);
        }
    },


    Mass(),

    Count() {
        @Override
        public com.bnorm.barkeep.measurement.type.Count parse(String str) {
            return com.bnorm.barkeep.measurement.type.Count.valueOf(str);
        }
    },

    Unitless(),

    // End of enumeration
    ;

    public Unit<?> parse(String str) {
        return null;
    }
}
