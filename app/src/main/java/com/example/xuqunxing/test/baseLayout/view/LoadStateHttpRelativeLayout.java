package com.example.xuqunxing.test.baseLayout.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.xuqunxing.test.baseLayout.view.inf.ILazyLoad;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class LoadStateHttpRelativeLayout extends LoadStateRelativeLayout implements ILazyLoad {

    public LoadStateHttpRelativeLayout(Context context) {
        this(context,null);
    }

    public LoadStateHttpRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadStateHttpRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean firstdata() {
        onLoadStart();
        loadData();
        return true;
    }
}
