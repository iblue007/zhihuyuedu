package com.example.xuqunxing.test.baseLayout.fragment.inf;

import android.view.View;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public interface ILoadFragmengState {

    /**
     * 加载开始
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_START = 0;
    /**
     * 加载为空
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_EMPTY = 1;
    /**
     * 加载失败
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_FAILED = 2;
    /**
     * 加载结束
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_FINISH = 3;
    /**
     * 加载没有网络
     */
    public final static int MESSAGE_ID_COMMON_LOADDATA_NOTWORK = 4;

    /**
     * 获取加载中视图
     *
     * @return
     */
    public View getLoadingView();

    /**
     * 获取加载失败视图
     *
     * @return
     */
    public View getLoadFailedView();

    /***
     * 获取加载数据为空视图
     *
     * @return
     */
    public View getEmptyView();

    /***
     * 获取内容视图
     *
     * @return
     */
    public View getContentView();

    /**
     * 没有网络
     */
//    View getNotNetworkView();

    /***
     * 初始化视图
     */
    public void initStateViews();

    /**
     * 开始加载
     */
    public void onLoadStart();

    /**
     * 加载失败
     */
    public void onLoadFailed();

    /**
     * 加载为空
     */
    public void onLoadEmpty();

    /**
     * 加载结束
     */
    public void onLoadSuccess();

    /**
     *无网络视图
     */
//    void onLoadNotNetwork();

    /**
     * 刷新视图
     */
    public void invalidateViews();

}
