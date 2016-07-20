package com.bnorm.barkeep.ui.book.edit;

import com.bnorm.barkeep.data.api.model.Book;

public interface EditBookView {

    void onClose();

    void onBookSaved(Book book);
}
