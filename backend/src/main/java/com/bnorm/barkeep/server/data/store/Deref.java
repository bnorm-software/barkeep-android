package com.bnorm.barkeep.server.data.store;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Ref;

public class Deref {
    private static class DerefFunc<T> implements Function<Ref<T>, T> {
        @SuppressWarnings("rawtypes") static DerefFunc INSTANCE = new DerefFunc();

        @Override
        public T apply(Ref<T> ref) {
            return deref(ref);
        }
    }

    private static class RefFunc<T> implements Function<T, Ref<T>> {
        @SuppressWarnings("rawtypes") public static RefFunc INSTANCE = new RefFunc();

        @Override
        public Ref<T> apply(T index) {
            return ref(index);
        }
    }

    public static <T> T deref(Ref<T> ref) {
        return ref == null ? null : ref.get();
    }

    public static <T> Ref<T> ref(T inst) {
        return inst == null ? null : Ref.create(inst);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> deref(List<Ref<T>> list) {
        return Lists.transform(list, DerefFunc.INSTANCE);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Ref<T>> ref(List<T> list) {
        return Lists.transform(list, RefFunc.INSTANCE);
    }
}
