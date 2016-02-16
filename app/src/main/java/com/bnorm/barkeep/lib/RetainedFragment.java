package com.bnorm.barkeep.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.google.common.base.Supplier;

public class RetainedFragment<E> extends Fragment {

    private E data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public static <E> RetainedFragment<E> init(FragmentManager fm, String tag, E data) {
        RetainedFragment<E> frag = retrieve(fm, tag);
        if (frag == null) {
            return create(fm, tag, data);
        } else {
            if (data != null) {
                frag.setData(data);
            }
            return frag;
        }
    }

    public static <E> RetainedFragment<E> init(FragmentManager fm, String tag, Supplier<E> data) {
        RetainedFragment<E> frag = retrieve(fm, tag);
        if (frag == null) {
            return create(fm, tag, data.get());
        } else {
            if (data != null) {
                E e = data.get();
                frag.setData(e);
            }
            return frag;
        }
    }

    public static <E> RetainedFragment<E> retrieve(FragmentManager fm, String tag) {
        @SuppressWarnings("unchecked")
        RetainedFragment<E> frag = (RetainedFragment<E>) fm.findFragmentByTag(tag);
        return frag;
    }

    public static <E> RetainedFragment<E> retrieve(FragmentManager fm, String tag, Supplier<E> data) {
        RetainedFragment<E> frag = retrieve(fm, tag);
        return frag == null ? create(fm, tag, data.get()) : frag;
    }

    public static <E> RetainedFragment<E> create(FragmentManager fm, String tag, E data) {
        RetainedFragment<E> frag = new RetainedFragment<>();
        fm.beginTransaction().add(frag, tag).commit();
        frag.setData(data);
        return frag;
    }
}
