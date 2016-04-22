package com.example.xuqunxing.test.loadview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.xuqunxing.test.loadview.inf.ILoadResult;

/**
 * Created by xuqunxing on 2016/4/21.
 */
public abstract class BaseFootView extends LinearLayout implements ILoadResult{

    /**
     * ListView回调
     */
    protected RefreshListView.IListViewCallBack mCallBack;

    public BaseFootView(Context context) {
        this(context,null);
    }

    public BaseFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmCallBack(RefreshListView.IListViewCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }
}
