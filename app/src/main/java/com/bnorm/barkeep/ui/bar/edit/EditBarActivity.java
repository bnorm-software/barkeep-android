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
import com.bnorm.barkeep.ui.ViewContainer;
import com.bnorm.barkeep.ui.bar.BarDetailActivity;
import com.bnorm.barkeep.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBarActivity extends BaseActivity implements EditBarView {

    // TODO: use new database format - title, description

    // ===== View ===== //

    @Inject ViewContainer viewContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.create_bar_title) AppCompatEditText title;
    @BindView(R.id.create_bar_description) AppCompatEditText description;

    // ===== Presenter ===== //

    @Inject
    EditBarPresenter presenter;

    public static void launch(Context source) {
        Intent intent = new Intent(source, EditBarActivity.class);
        source.startActivity(intent);
    }

    public static void launch(Context source, Bar bar) {
        Intent intent = new Intent(source, EditBarActivity.class);
        intent.putExtra(BarDetailActivity.BAR_TAG, bar);
        source.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barkeep().component().plus(new EditBarViewModule(this)).inject(this);

        ViewGroup container = viewContainer.forActivity(this);
        getLayoutInflater().inflate(R.layout.activity_create_bar, container);
        ButterKnife.bind(this, container);

        loadBar(getIntent().getParcelableExtra(BarDetailActivity.BAR_TAG));
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

    @OnClick(R.id.create_bar_cancel)
    void onCancel() {
        presenter.cancel();
    }

    @OnClick(R.id.create_bar_save)
    void onSave() {
        presenter.save(getBar());
    }

    @Override
    public void onClose() {
        onBackPressed();
    }

    @Override
    public void onBarSaved(Bar bar) {
        Toast.makeText(getApplicationContext(), "Saved " + bar.getName(), Toast.LENGTH_LONG).show();
    }
}
