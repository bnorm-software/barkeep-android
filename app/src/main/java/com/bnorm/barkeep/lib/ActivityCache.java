package com.bnorm.barkeep.lib;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public enum ActivityCache {
    instance;

    private final Cache<Class<?>, Object> cache;

    ActivityCache() {
        this.cache = CacheBuilder.newBuilder().weakValues().build();
    }

    public <T> void post(T t) {
        cache.put(t.getClass(), t);
    }

    public <T> T grab(Class<T> clazz) {
        return clazz.cast(cache.getIfPresent(clazz));
    }

    public <T> T steal(Class<T> clazz) {
        T t = grab(clazz);
        cache.invalidate(clazz);
        return t;
    }
}
