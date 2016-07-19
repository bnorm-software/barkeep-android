package com.bnorm.barkeep.ui.book;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {BookListViewModule.class})
public interface BookListFragmentComponent {

    void inject(BookListFragment fragment);
}
