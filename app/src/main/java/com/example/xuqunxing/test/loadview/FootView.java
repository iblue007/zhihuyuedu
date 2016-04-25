package com.example.xuqunxing.test.loadview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.view.ProgressBarCircularIndeterminate;

/**
 * Created by xuqunxing on 2016/4/21.
 */
public class FootView extends BaseFootView{

    /**
     * 等待
     */
    protected ProgressBarCircularIndeterminate mloadingPb;
    /**
     * 全部加载完成
     */
    protected TextView mLoadCompTv;

    /**
     * 空提示
     */
    protected TextView mLoadEmptyTv;

    public FootView(Context context) {
        this(context,null);
    }

    public FootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }

    private void initview() {
        LayoutParams mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.foot_view, null);
        mLayoutParams.gravity = Gravity.CENTER;

        addView(view, mLayoutParams);
        mloadingPb = (ProgressBarCircularIndeterminate) findViewById(R.id.load_ing_pb);
        mLoadCompTv = (TextView) findViewById(R.id.load_complete_tv);
        mLoadEmptyTv = (TextView) findViewById(R.id.load_empty_tv);
        mLoadEmptyTv.setOnClickListener(mClickListener);
    }

    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == mLoadEmptyTv.getId()) {
                if (mCallBack != null) {
                    mloadingPb.setVisibility(View.VISIBLE);
                    mLoadCompTv.setVisibility(View.GONE);
                    mLoadEmptyTv.setVisibility(View.GONE);
                    mCallBack.onUpLoad();
                }
            }
        }
    };

    @Override
    public void onLoadFailed() {
        dispathShowView(mLoadEmptyTv);
    }

    @Override
    public void onLoadSuccess() {
        dispathShowView(mloadingPb);
    }

    @Override
    public void onLoadComplete() {
        dispathShowView(mLoadCompTv);
    }

    @Override
    public void onLoadEmpty() {
        dispathShowView(mLoadEmptyTv);
    }

    @Override
    public void onLoadStart() {
        dispathShowView(mloadingPb);
    }

    /**
     * 显示哪个view
     *
     * @param v
     */
    protected void dispathShowView(View v) {

        if (mloadingPb.getId() == v.getId()) {
            mloadingPb.setVisibility(View.VISIBLE);
            mLoadCompTv.setVisibility(View.GONE);
            mLoadEmptyTv.setVisibility(View.GONE);
            return;
        }
        if (mLoadCompTv.getId() == v.getId()) {
            mloadingPb.setVisibility(View.GONE);
            mLoadCompTv.setVisibility(View.GONE);
            mLoadEmptyTv.setVisibility(View.GONE);
            return;
        }
        if (mLoadEmptyTv.getId() == v.getId()) {
            mloadingPb.setVisibility(View.GONE);
            mLoadCompTv.setVisibility(View.GONE);
            mLoadEmptyTv.setVisibility(View.VISIBLE);
            mLoadEmptyTv.setText("获取内容失败");
            return;
        }
    }
}
