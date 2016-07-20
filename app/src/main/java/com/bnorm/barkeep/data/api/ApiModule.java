package com.bnorm.barkeep.data.api;

import java.util.Set;

import com.bnorm.barkeep.data.api.net.NetScope;
import com.google.common.collect.ImmutableSet;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class ApiModule {

    private final HttpUrl httpUrl;

    public ApiModule(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    @ApiScope
    @Provides(type = Provides.Type.SET_VALUES)
    Set<JsonAdapter.Factory> provideJsonAdapterFactories() {
        return ImmutableSet.of();
    }

    @ApiScope
    @Provides
    Moshi provideMoshi(Set<JsonAdapter.Factory> adapterFactories) {
        Moshi.Builder builder = new Moshi.Builder();
        for (JsonAdapter.Factory adapterFactory : adapterFactories) {
            builder.add(adapterFactory);
        }
        return builder.build();
    }

    @ApiScope
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Moshi moshi) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(MoshiConverterFactory.create(moshi));
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.baseUrl(httpUrl);
        builder.client(okHttpClient);
        return builder.build();
    }

    @ApiScope
    @Provides
    BarkeepService provideBarkeepService(Retrofit retrofit) {
        return retrofit.create(BarkeepService.class);
    }


    @ApiScope
    @Provides
    @ApiScheduler
    Scheduler provideApiScheduler() {
        return Schedulers.io();
    }
}
