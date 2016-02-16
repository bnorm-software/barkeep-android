package com.bnorm.barkeep.lib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class WrappingLinearLayoutManager extends LinearLayoutManager {

    private final int[] mMeasuredDimension;

    public WrappingLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.mMeasuredDimension = new int[2];
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int width = 0;
        int height = 0;
        int itemCount = state.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            int spec = View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED);
            if (getOrientation() == HORIZONTAL) {
                measureScrapChild(recycler, i, spec, heightSpec, mMeasuredDimension);
                width = width + mMeasuredDimension[0];
                if (i == 0) {
                    height = mMeasuredDimension[1];
                }
            } else {
                measureScrapChild(recycler, i, widthSpec, spec, mMeasuredDimension);
                height = height + mMeasuredDimension[1];
                if (i == 0) {
                    width = mMeasuredDimension[0];
                }
            }
        }
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec,
                                   int[] measuredDimension) {
        View view = recycler.getViewForPosition(position);
        recycler.bindViewToPosition(view, position);
        if (view != null) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                                                               getPaddingLeft() + getPaddingRight(),
                                                               p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                                                                getPaddingTop() + getPaddingBottom(),
                                                                p.height);
            view.measure(childWidthSpec, childHeightSpec);
            measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
            measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }
}
