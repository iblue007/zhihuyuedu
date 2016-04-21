package com.example.xuqunxing.test.baseLayout.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xuqunxing.test.baseLayout.fragment.inf.ILoadFragmengState;
import com.example.xuqunxing.test.baseLayout.view.inf.ILoadState;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class LoadStateFragment extends BaseLoadFragment implements ILoadFragmengState {

    private View mLoadingView;
    private View mLoadFailedView;
    protected View mContentView;
    private View mEmptyView;

    private Handler mhandler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what){
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
                    invalidateViews();
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
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=super.onCreateView(inflater,container,savedInstanceState);
        initStateViews();
        return view;
    }

    @Override
    public void initStateViews() {
        mContentView=getContentView();
        mLoadingView=getLoadingView();
        mLoadFailedView=getLoadFailedView();
        mEmptyView=getEmptyView();
    }

    @Override
    public void onLoadStart() {
        mhandler.sendEmptyMessage(ILoadFragmengState.MESSAGE_ID_COMMON_LOADDATA_START);
    }

    @Override
    public void onLoadFailed() {
        mhandler.sendEmptyMessage(ILoadFragmengState.MESSAGE_ID_COMMON_LOADDATA_FAILED);
    }

    @Override
    public void onLoadEmpty() {
        mhandler.sendEmptyMessage(ILoadFragmengState.MESSAGE_ID_COMMON_LOADDATA_EMPTY);
    }

    @Override
    public void onLoadSuccess() {
        mhandler.sendEmptyMessage(ILoadFragmengState.MESSAGE_ID_COMMON_LOADDATA_FINISH);
    }
}
