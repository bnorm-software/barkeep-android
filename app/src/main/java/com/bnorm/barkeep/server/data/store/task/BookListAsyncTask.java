package com.bnorm.barkeep.server.data.store.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import com.bnorm.barkeep.server.data.store.Book;
import com.bnorm.barkeep.server.data.store.Endpoints;

public class BookListAsyncTask extends AsyncTask<Void, Void, List<Book>> {

    @Override
    protected List<Book> doInBackground(Void... params) {
        try {
            List<com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book> books;
            books = Endpoints.instance.getEndpoint().listBooks().execute().getItems();
            if (books != null) {
                List<Book> items = new ArrayList<>();
                for (com.bnorm.barkeep.server.data.store.v1.endpoint.model.Book book : books) {
                    items.add(new Book(book));
                }
                return items;
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
