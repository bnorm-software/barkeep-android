package com.bnorm.barkeep.server.data.store;

import android.os.Parcel;
import android.os.Parcelable;

public class Amount implements Parcelable {

    private Double recommended;
    private Double min;
    private Double max;

    public Amount() {
    }

    public Amount(com.bnorm.barkeep.server.data.store.v1.endpoint.model.Amount amount) {
        this.recommended = amount.getRecommended();
        this.min = amount.getMin();
        this.max = getMax();
    }

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

    public com.bnorm.barkeep.server.data.store.v1.endpoint.model.Amount toStore() {
        com.bnorm.barkeep.server.data.store.v1.endpoint.model.Amount amount;
        amount = new com.bnorm.barkeep.server.data.store.v1.endpoint.model.Amount();
        amount.setRecommended(recommended);
        amount.setMin(min);
        amount.setMax(max);
        return amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.recommended);
        dest.writeValue(this.min);
        dest.writeValue(this.max);
    }

    protected Amount(Parcel in) {
        this.recommended = (Double) in.readValue(Double.class.getClassLoader());
        this.min = (Double) in.readValue(Double.class.getClassLoader());
        this.max = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Amount> CREATOR = new Parcelable.Creator<Amount>() {
        public Amount createFromParcel(Parcel source) {
            return new Amount(source);
        }

        public Amount[] newArray(int size) {
            return new Amount[size];
        }
    };
}
