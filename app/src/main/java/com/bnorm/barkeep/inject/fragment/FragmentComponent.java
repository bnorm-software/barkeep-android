package com.bnorm.barkeep.inject.fragment;

import com.bnorm.barkeep.inject.activity.ActivityComponent;
import dagger.Component;

@FragmentScope
@Component(modules = {}, dependencies = {ActivityComponent.class})
public interface FragmentComponent extends ActivityComponent {
}
