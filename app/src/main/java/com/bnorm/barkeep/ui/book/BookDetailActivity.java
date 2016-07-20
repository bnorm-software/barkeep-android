package com.bnorm.barkeep.ui.book;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.book.edit.EditBookActivity;

public class BookDetailActivity extends BaseActivity {
    private static final String BOOK_TAG = "book";

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;


    public static void launch(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_TAG, book);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barkeep().component().plus(new BookDetailViewModule()).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_book_detail, container);
        ButterKnife.bind(this, container);

        Book book = Bundles.getParcelable(BOOK_TAG, getIntent().getExtras());
        assert book != null;

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(book.getName());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert book != null;
        assert fab != null;

        fab.setOnClickListener(view -> EditBookActivity.launch(this, book));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
