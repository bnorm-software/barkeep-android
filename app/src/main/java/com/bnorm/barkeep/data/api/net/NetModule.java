package com.bnorm.barkeep.data.api.net;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.bnorm.barkeep.data.api.WireTraceInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
public class NetModule {

    private final File cacheDir;
    private final int cacheSize;

    public NetModule(File cacheDir) {
        this.cacheDir = cacheDir;
        this.cacheSize = 10 * 1024 * 1024; // 10 MiB
    }

    @NetScope
    @Provides
    Cache provideOkHttpCache() {
        return new Cache(cacheDir, cacheSize);
    }

    @NetScope
    @Provides
    CacheInterceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    @NetScope
    @Provides(type = Provides.Type.SET_VALUES)
    Set<Interceptor> provideInterceptors(CacheInterceptor cacheInterceptor) {
        return new LinkedHashSet<>(Arrays.asList(cacheInterceptor,
                                                 // new SessionInterceptor(),
                                                 // new TokenInterceptor(),
                                                 new WireTraceInterceptor()));
    }

    @NetScope
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache, Set<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }
}
