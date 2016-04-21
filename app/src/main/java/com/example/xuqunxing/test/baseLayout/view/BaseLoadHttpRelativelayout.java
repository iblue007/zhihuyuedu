package com.example.xuqunxing.test.baseLayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.xuqunxing.test.baseLayout.view.loadstate.LoadstatueViewFactory;
import com.example.xuqunxing.test.util.ToastUtils;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class BaseLoadHttpRelativelayout extends LoadStateHttpRelativeLayout{

    public BaseLoadHttpRelativelayout(Context context) {
        super(context);
    }

    public BaseLoadHttpRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLoadHttpRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View getLoadingView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getLoadingViewRw(getContext(), mContentView);
        }
        return null;
    }

    @Override
    public View getLoadFailedView() {
        if (mContentView != null) {
            LoadstatueViewFactory.getEmptyViewRw(getContext(), mContentView, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(getContext(), "empty");
                }
            });
        }
        return null;
    }

    @Override
    public View getEmptyView() {
        if (mContentView != null) {
            LoadstatueViewFactory.getEmptyViewRw(getContext(), mContentView, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(getContext(), "empty");
                }
            });
        }
        return null;
    }

    @Override
    public void loadData() {
        loadData(1);
    }

    @Override
    public void invalidateViews() {

    }
}
