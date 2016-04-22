package com.example.xuqunxing.test.loadview.inf;

/**
 * Created by xuqunxing on 2016/4/21.
 */
public interface ILoadResult {

    /**
     * 加载成功
     */
    public void onLoadFailed();

    /**
     * 加载成功
     */
    public void onLoadSuccess();

    /**
     * 完全结束
     */
    public void onLoadComplete();
    /**
     * 加载未空
     */
    public void onLoadEmpty();
    /**
     * 开始加载
     */
    public void onLoadStart();
}
