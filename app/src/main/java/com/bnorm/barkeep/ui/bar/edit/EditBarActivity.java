package com.bnorm.barkeep.ui.bar.edit;

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
import com.bnorm.barkeep.data.api.model.Bar;
import com.bnorm.barkeep.lib.Bundles;
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBarActivity extends BaseActivity implements EditBarView {
    private static final String BAR_TAG = "bar";

    // TODO: use new database format - title, description

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edit_bar_title) AppCompatEditText title;
    @BindView(R.id.edit_bar_description) AppCompatEditText description;

    // ===== Presenter ===== //

    @Inject
    EditBarPresenter presenter;

    public static void launch(Context source) {
        Intent intent = new Intent(source, EditBarActivity.class);
        source.startActivity(intent);
    }

    public static void launch(Context source, Bar bar) {
        Intent intent = new Intent(source, EditBarActivity.class);
        intent.putExtra(BAR_TAG, bar);
        source.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barkeep().component().plus(new EditBarViewModule(this)).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_edit_bar, container);
        ButterKnife.bind(this, container);

        loadBar(Bundles.getParcelable(BAR_TAG, getIntent().getExtras()));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    public void onStop() {
        presenter.detach();
        super.onStop();
    }

    @NonNull
    private Bar getBar() {
        Bar bar = new Bar();
        bar.setName(title.getText().toString());
        return bar;
    }

    private void loadBar(@Nullable Bar bar) {
        if (bar != null) {
            title.setText(bar.getName());
        }
    }

    @OnClick(R.id.edit_bar_cancel)
    void onCancel() {
        // todo(bnorm) are you sure? - if there are changed fields
        onBackPressed();
    }

    @OnClick(R.id.edit_bar_save)
    void onSave() {
        // todo(bnorm) check fields are properly filled
        presenter.save(getBar());
    }

    @Override
    public void onBarSaved(Bar bar) {
        Toast.makeText(getApplicationContext(), "Saved " + bar.getName(), Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}
