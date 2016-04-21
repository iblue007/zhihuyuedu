package com.example.xuqunxing.test.baseLayout.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.xuqunxing.test.baseLayout.view.inf.ILoadState;

/**加载状态视图
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class LoadStateRelativeLayout extends BaseRelativeLayout implements ILoadState{

    protected View mLoadingView;
    protected View mLoadFailedView;
    protected View mContentView;
    protected View mEmptyView;

    public LoadStateRelativeLayout(Context context) {
        this(context, null);
    }

    public LoadStateRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStateViews();
    }

    private Handler mHandler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what){
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_START:
                    showStartLoadView();
                    break;
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_FAILED:
                    showFailView();
                    break;
                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_EMPTY:
                    showEmptyView();
                    break;

                case ILoadState.MESSAGE_ID_COMMON_LOADDATA_FINISH:
                    showSuccessView();
                    break;
            }
        }
    };

    private void showStartLoadView() {
        mLoadingView.setVisibility(VISIBLE);
        mContentView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mLoadFailedView.setVisibility(GONE);
    }

    private void showFailView() {
        mLoadingView.setVisibility(GONE);
        mContentView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mLoadFailedView.setVisibility(VISIBLE);
    }

    private void showEmptyView() {
        mLoadingView.setVisibility(GONE);
        mContentView.setVisibility(GONE);
        mEmptyView.setVisibility(VISIBLE);
        mLoadFailedView.setVisibility(GONE);
    }

    private void showSuccessView() {
        mLoadingView.setVisibility(GONE);
        mContentView.setVisibility(VISIBLE);
        mEmptyView.setVisibility(GONE);
        mLoadFailedView.setVisibility(GONE);
    }

    public void initStateViews(){
        mContentView=getContentView();
        mEmptyView=getEmptyView();
        mLoadingView=getLoadingView();
        mLoadFailedView=getLoadFailedView();
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
