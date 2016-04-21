package com.example.xuqunxing.test.baseLayout.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.xuqunxing.test.baseLayout.activity.inf.ILoadState;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class LoadstateActivity extends BaseActivity implements ILoadState {


    protected View mLoadingView;
    protected View mLoadFailedView;
    protected View mContentView;
    protected View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateViews();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_EMPTY:
                    mLoadingView.setVisibility(View.GONE);
                    mLoadFailedView.setVisibility(View.GONE);
                    mContentView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                    break;
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_FAILED:
                    mLoadingView.setVisibility(View.GONE);
                    mLoadFailedView.setVisibility(View.VISIBLE);
                    mContentView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    break;
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_FINISH:
                    mLoadingView.setVisibility(View.GONE);
                    mLoadFailedView.setVisibility(View.GONE);
                    mContentView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    break;
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_START:
                    mLoadingView.setVisibility(View.VISIBLE);
                    mLoadFailedView.setVisibility(View.GONE);
                    mContentView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        };
    };

    public void initStateViews() {
        mContentView = getContentView();
        mLoadingView = getLoadingView();
        mLoadFailedView = getLoadFailedView();
        mEmptyView = getEmptyView();
    }

    @Override
    public void onLoadStart() {
        mHandler.sendEmptyMessage(ILoadState.MESSAGE_ID_COMMON_LOADDATA_START);
    }

    @Override
    public void onLoadFailed() {
        mHandler.sendEmptyMessage(ILoadState.MESSAGE_ID_COMMON_LOADDATA_FAILED);
    }

    @Override
    public void onLoadEmpty() {
        mHandler.sendEmptyMessage(ILoadState.MESSAGE_ID_COMMON_LOADDATA_EMPTY);
    }

    @Override
    public void onLoadSuccess() {
        mHandler.sendEmptyMessage(ILoadState.MESSAGE_ID_COMMON_LOADDATA_FINISH);
    }
}
