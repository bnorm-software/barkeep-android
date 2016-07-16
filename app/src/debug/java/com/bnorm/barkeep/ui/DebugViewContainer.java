package com.bnorm.barkeep.ui;

import android.app.Activity;
import android.os.PowerManager;
import android.view.ViewGroup;

import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public final class DebugViewContainer implements ViewContainer {

    @Override
    public ViewGroup forActivity(Activity activity) {
        riseAndShine(activity);
        return DEFAULT.forActivity(activity);
    }

    private static void riseAndShine(Activity activity) {
        activity.getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager power = (PowerManager) activity.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock lock = power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE,
                                                       "wakeup!");
        lock.acquire();
        lock.release();
    }
}
