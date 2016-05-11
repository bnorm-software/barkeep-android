package com.bnorm.barkeep.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.bnorm.barkeep.inject.fragment.DaggerFragmentComponent;
import com.bnorm.barkeep.inject.fragment.FragmentComponent;
import com.bnorm.barkeep.ui.base.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    private FragmentComponent component;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerFragmentComponent.builder()
                                           .activityComponent(((BaseActivity) getActivity()).component())
                                           .build();
    }

    public FragmentComponent component() {
        return component;
    }
}
