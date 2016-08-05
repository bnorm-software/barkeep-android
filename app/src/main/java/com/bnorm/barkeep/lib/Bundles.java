package com.bnorm.barkeep.lib;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public final class Bundles {
    private Bundles() {
    }

    @Nullable
    public static <T extends Parcelable> T getParcelable(String tag, Bundle... bundles) {
        for (Bundle bundle : bundles) {
            if (bundle != null) {
                T parcelable = bundle.getParcelable(tag);
                if (parcelable != null) {
                    return parcelable;
                }
            }
        }
        return null;
    }
}
