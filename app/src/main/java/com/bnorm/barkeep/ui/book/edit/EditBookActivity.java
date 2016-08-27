package com.bnorm.barkeep.ui.book.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bnorm.barkeep.R;
import com.bnorm.barkeep.data.api.model.Book;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;
import com.bnorm.barkeep.ui.book.BookDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBookActivity extends BaseActivity implements EditBookView {

    // TODO: use new database format - title, description

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edit_book_title) AppCompatEditText title;
    @BindView(R.id.edit_book_description) AppCompatEditText description;

    // ===== Presenter ===== //

    @Inject EditBookPresenter presenter;

    public static void launch(Context source) {
        Intent intent = new Intent(source, EditBookActivity.class);
        source.startActivity(intent);
    }

    public static void launch(Context source, Book book) {
        Intent intent = new Intent(source, EditBookActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_TAG, book);
        source.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barkeep().component().plus(new EditBookViewModule(this)).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_edit_book, container);
        ButterKnife.bind(this, container);

        loadBook(Bundles.getParcelable(BookDetailActivity.BOOK_TAG, getIntent().getExtras()));
    }

    @NonNull
    private Book getBook() {
        Book book = new Book();
        book.setName(title.getText().toString());
        return book;
    }

    private void loadBook(@Nullable Book book) {
        if (book != null) {
            title.setText(book.getName());
        }
    }

    @OnClick(R.id.edit_book_cancel)
    void onCancel() {
        presenter.cancel();
    }

    @OnClick(R.id.edit_book_save)
    void onSave() {
        presenter.save(getBook());
    }

    @Override
    public void onClose() {
        onBackPressed();
    }

    @Override
    public void onBookSaved(Book book) {
        Toast.makeText(getApplicationContext(), "Saved " + book.getName(), Toast.LENGTH_LONG).show();
    }
}
