package com.bnorm.barkeep.inject.activity;

import com.bnorm.barkeep.inject.app.AppComponent;
import com.bnorm.barkeep.inject.app.AppScope;
import dagger.Component;

@ActivityScope
@Component(modules = {}, dependencies = {AppComponent.class})
public interface ActivityComponent extends AppComponent {
}
