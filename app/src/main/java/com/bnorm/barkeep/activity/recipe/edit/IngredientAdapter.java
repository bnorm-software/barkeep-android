package com.bnorm.barkeep.activity.recipe.edit;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.bnorm.barkeep.R;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final RecyclerView mRecyclerView;
    private final List<String> mItems;

    public IngredientAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mItems = new ArrayList<>();
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_edit_ingredient, parent, false);
        TextView leadingText = (TextView) v.findViewById(R.id.ingredient_edit_leading_text);
        EditText name = (EditText) v.findViewById(R.id.ingredient_edit);
        return new IngredientViewHolder(v, leadingText, name);
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

    public String get(int i) {
        return mItems.get(i);
    }

    public void add(String str) {
        mItems.add(str);
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView mLeadingText;
        private final EditText mName;

        private IngredientViewHolder(View v, TextView leadingText, EditText name) {
            super(v);
            this.mLeadingText = leadingText;
            this.mName = name;

            this.mName.setOnFocusChangeListener(new FocusChangeListener());
            this.mName.addTextChangedListener(new TextListener());
            this.mName.setOnEditorActionListener(new EditorActionListener());
        }

        private class EditorActionListener implements TextView.OnEditorActionListener {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int position = getLayoutPosition();
                RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForLayoutPosition(position + 1);
                if (holder == null) {
                    mRecyclerView.smoothScrollToPosition(position + 1);
                    return true;
                }
                return false;
            }
        }

        private class TextListener implements TextWatcher {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int position = getLayoutPosition();
                if (position < mItems.size()) {
                    mItems.set(position, s.toString());
                }
            }
        }

        private class FocusChangeListener implements View.OnFocusChangeListener {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (getLayoutPosition() == mItems.size() - 1) {
                        // The last edit box just gained focus
                        mItems.add("");
                        notifyItemInserted(mItems.size() - 1);
                    }
                } else {
                    // The view just lost focus
                    int end = mItems.size() - 1;
                    int start = end;
                    while (start > 0 && mItems.get(start).isEmpty() && mItems.get(start - 1).isEmpty()) {
                        mItems.remove(start);
                        start--;
                    }
                    if (start != end) {
                        notifyItemRangeRemoved(start + 1, end - start);
                    }
                }
            }
        }
    }
}
