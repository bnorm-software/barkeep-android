package com.bnorm.barkeep.ui.book.list;

import java.util.List;

import com.bnorm.barkeep.data.api.model.Book;

public interface BookListView {

    void onBooks(List<Book> books);

    void onBookDetail(Book book);
}
