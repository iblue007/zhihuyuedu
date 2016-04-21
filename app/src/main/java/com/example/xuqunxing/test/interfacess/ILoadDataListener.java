package com.example.xuqunxing.test.interfacess;

/**
 * Created by xuqunxing on 2016/3/29.
 */
public interface ILoadDataListener {
    //get请求
    public void loadDataByGet(String url,IUIDataListener listener);


    //post请求
    public void loadDataByPost(String url,IUIDataListener listener);
}
