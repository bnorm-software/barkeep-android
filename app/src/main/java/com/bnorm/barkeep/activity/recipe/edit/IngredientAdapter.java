package com.bnorm.barkeep.activity.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bnorm.barkeep.R;
import com.jakewharton.rxbinding.widget.RxTextView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final RecyclerView mRecyclerView;
    private final List<CharSequence> mItems;

    public IngredientAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mItems = new ArrayList<>();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext())
                                                      .inflate(R.layout.item_edit_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.mLeadingText.setText(position == 0 ? "" : "or ");
        holder.mName.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public CharSequence get(int i) {
        return mItems.get(i);
    }

    public void add(CharSequence str) {
        mItems.add(str);
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_edit_leading_text) TextView mLeadingText;
        @BindView(R.id.ingredient_edit) EditText mName;

        private IngredientViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            // Navigate to next ingredient on enter
            RxTextView.editorActions(this.mName).subscribe(id -> {
                int position = getLayoutPosition() + 1;
                // todo why is this == null?
                if (mRecyclerView.findViewHolderForLayoutPosition(position) == null) {
                    mRecyclerView.smoothScrollToPosition(position);
                }
            });

            RxTextView.textChanges(this.mName).subscribe(text -> {
                if (text.length() == 0) {
                    return;
                }

                // Update the adapter list when text changes
                mItems.set(getLayoutPosition(), text);

                // If text is added to or removed from the last edit box
                if (text.length() == 0) {
                    int end = mItems.size() - 1;
                    int start = end;
                    while (start > 0 && mItems.get(start).length() == 0 && mItems.get(start - 1).length() == 0) {
                        mItems.remove(start);
                        start--;
                    }
                    if (start != end) {
                        notifyItemRangeRemoved(start + 1, end - start);
                    }
                } else {
                    if (getLayoutPosition() == mItems.size() - 1) {
                        mItems.add("");
                        notifyItemInserted(mItems.size() - 1);
                    }
                }
            });
        }
    }
}
