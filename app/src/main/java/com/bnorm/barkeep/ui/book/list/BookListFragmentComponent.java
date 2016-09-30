package com.bnorm.barkeep.ui.book.list;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {BookListViewModule.class})
public interface BookListFragmentComponent {

    void inject(BookListFragment fragment);
}
