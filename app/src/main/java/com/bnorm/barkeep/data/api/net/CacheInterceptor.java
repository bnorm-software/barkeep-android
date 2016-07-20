package com.bnorm.barkeep.data.api.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class CacheInterceptor implements Interceptor {

    private static final CacheControl DEFAULT = new CacheControl.Builder().maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS)
                                                                          .build();

    private boolean refresh = false;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (refresh) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
        } else {
            request = request.newBuilder().cacheControl(DEFAULT).build();
        }
        return chain.proceed(request);
    }

    public void refresh(boolean refresh) {
        this.refresh = refresh;
    }
}
