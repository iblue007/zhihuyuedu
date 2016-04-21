package com.example.xuqunxing.test.baseLayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.xuqunxing.test.baseLayout.view.inf.IinitView;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract  class BaseRelativeLayout extends RelativeLayout implements IinitView {

    public BaseRelativeLayout(Context context) {
        this(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initView();
        initData();
        initListener();
    }
}
