package com.bnorm.barkeep.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public final class Retained<E> extends Fragment {

    private E data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public E get(E fallback) {
        if (data == null) {
            data = fallback;
        }
        return data;
    }

    public E get() {
        return data;
    }

    public void set(E data) {
        this.data = data;
    }


    public static <E> E retain(Fragment parent, String name, E fallback) {
        return Retained.<E>init(parent, name).get(fallback);
    }

    public static <E> E retain(Fragment parent, String name) {
        return Retained.<E>init(parent, name).get();
    }


    public static <E> Retained<E> init(FragmentManager fm, String tag) {
        Retained<E> frag = find(fm, tag);
        if (frag == null) {
            frag = create(fm, tag);
        }
        return frag;
    }

    public static <E> Retained<E> init(FragmentManager fm, Class<?> klass, String name) {
        return init(fm, klass.getName() + "." + name);
    }

    public static <E> Retained<E> init(Fragment parent, String name) {
        return init(parent.getFragmentManager(), parent.getClass(), name);
    }


    public static <E> Retained<E> find(FragmentManager fm, String tag) {
        @SuppressWarnings("unchecked")
        Retained<E> frag = (Retained<E>) fm.findFragmentByTag(tag);
        return frag;
    }

    public static <E> Retained<E> create(FragmentManager fm, String tag) {
        Retained<E> frag = new Retained<>();
        fm.beginTransaction().add(frag, tag).commit();
        return frag;
    }
}
