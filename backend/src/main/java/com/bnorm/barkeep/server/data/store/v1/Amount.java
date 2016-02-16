package com.bnorm.barkeep.server.data.store.v1;

import com.google.api.server.spi.config.ApiTransformer;

//@ApiTransformer(Amount.Transformer.class)
public class Amount {
    private Double recommended;
    private Double min;
    private Double max;

    public Double getRecommended() {
        return recommended;
    }

    public void setRecommended(Double recommended) {
        this.recommended = recommended;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

//    public static class Transformer implements com.google.api.server.spi.config.Transformer<Amount, String> {
//        @Override
//        public String transformTo(Amount amount) {
//            if (amount.getRecommended() != null) {
//                return String.valueOf(amount.getRecommended());
//            } else {
//                return String.valueOf(amount.getMin()) + "," + String.valueOf(amount.getMax());
//            }
//        }
//
//        @Override
//        public Amount transformFrom(String s) {
//            Amount amount = new Amount();
//
//            String[] split = s.split(",");
//            if (split.length == 2) {
//                amount.setMin(Double.valueOf(split[0]));
//                amount.setMax(Double.valueOf(split[1]));
//            } else {
//                amount.setRecommended(Double.valueOf(split[0]));
//            }
//
//            return amount;
//        }
//    }
}
