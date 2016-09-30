package com.bnorm.barkeep.ui.book.detail;

import com.bnorm.barkeep.ui.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {BookDetailViewModule.class})
public interface BookDetailActivityComponent {

    void inject(BookDetailActivity activity);
}
